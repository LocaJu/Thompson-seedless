package com.seed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.domain.ResponseResult;
import com.seed.domain.dto.ArticleDto;
import com.seed.domain.entity.Article;
import com.seed.domain.entity.ArticleTag;
import com.seed.domain.entity.Category;
import com.seed.domain.vo.ArticleDetailVo;
import com.seed.domain.vo.ArticleListVo;
import com.seed.domain.vo.HotArticleVo;
import com.seed.domain.vo.PageVo;
import com.seed.mapper.ArticleMapper;
import com.seed.service.ArticleService;
import com.seed.service.ArticleTagService;
import com.seed.service.CategoryService;
import com.seed.utils.BeanCopyUtils;
import com.seed.utils.RedisCache;
import com.seed.utils.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.seed.constants.SystemConstants.ARTICLE_STATUS_NORMAL;


/**
* @author 77286
* @description 针对表【tb_article(文章表)】的数据库操作Service实现
* @createDate 2023-12-10 16:18:36
*/
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService{

    @Value("${qiniu.CDN}")
    String CDN;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;


    @Override
    public ResponseResult getList() {
        //查询热门文章
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        //正式文章  （非正在编辑文章）
        queryWrapper.eq(Article::getStatus,ARTICLE_STATUS_NORMAL);
        //按照浏览进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //分页
        int page=1;
        int size=10;
        Page<Article> articlePage=new Page<>(page,size);
        page(articlePage,queryWrapper);

        List<Article> records = articlePage.getRecords();

        //bean拷贝
//        List<HotArticleVo> articleVos=new ArrayList<>();
//        for (Article record : records) {
//            HotArticleVo vo=new HotArticleVo();
//            BeanUtils.copyProperties(record,vo);
//            articleVos.add(vo);
//        }
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);

        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //条件查询
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();

        //当根据标签查询时
        articleLambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);

        //状态是否时正式发布（非编辑状态）
        articleLambdaQueryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        //根据istop优先级（是否置顶）排序
        articleLambdaQueryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page=new Page<>(pageNum==null?1:pageNum,pageSize==null?10:pageSize);
        page(page,articleLambdaQueryWrapper);

        //查询categoryName
        List<Article> articles = page.getRecords();
        //根据categoryId去Category查询对应名称
        //方式一
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategory_id());
//            article.setCategory_name(category.getName());
//        }
        //方式二 lambda
        articles.stream().map(s -> s.setCategoryName(categoryService.getById(s.getCategoryId()).getName())).collect(Collectors.toList());


        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    //查询文章详情
    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取浏览量
        Integer viewCount = redisCache.getCacheMapValue(RedisConstants.ARTICLE_VIEW_COUNT, String.valueOf(id));
        // 处理viewCount为null的情况
        if (viewCount != null) {
            article.setViewCount(viewCount.longValue());
        } else {
            // 如果Redis中没有，使用数据库中的值，如果也为null则默认为0
            article.setViewCount(article.getViewCount() != null ? article.getViewCount() : 0L);
        }
        //转换为VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应
        return ResponseResult.okResult(articleDetailVo);
    }

    //更新redis中的浏览量
    @Override
    public ResponseResult updateViewCount(Long id) {
        Long viewCount = redisCache.incrementCacheMapValue(RedisConstants.ARTICLE_VIEW_COUNT, String.valueOf(id), null);
        return ResponseResult.okResult(viewCount);
    }




        @Override
        @Transactional
        public ResponseResult add(ArticleDto articleDto) {
            //添加 博客
            Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
            /**
            * 保存图片的相对路径----->查询时加上域名
            *                 http://s66bj54ml.bkt.clouddn.com/2023/12/30/2/2eacc66309ade14644a6b9b4f9bbf0ee/2eacc66309ade14644a6b9b4f9bbf0ee.csv
            *得到文件路径 如：                                   /2023/12/30/2/2eacc66309ade14644a6b9b4f9bbf0ee/2eacc66309ade14644a6b9b4f9bbf0ee.csv
            */
            //将文章中的路径域名去掉（数据库中存储文件相对路径）
            String thumbnailUrl = article.getThumbnail();
            if (thumbnailUrl != null && !thumbnailUrl.trim().isEmpty()) {
                // 如果包含CDN域名，则去掉域名，只保留相对路径
                if (thumbnailUrl.contains(CDN)) {
                    String filepath = thumbnailUrl.replace(CDN, "");
                    article.setThumbnail(filepath);
                }
                // 如果不包含CDN域名，说明已经是相对路径，直接使用
            } else {
                // 如果缩略图为空，设置为null
                article.setThumbnail(null);
            }

            //保存文章
            save(article);
            List<ArticleTag> articleTags = articleDto.getTags().stream()
                    .map(tagId -> new ArticleTag(article.getId(), tagId))
                    .collect(Collectors.toList());
            //添加 博客和标签的关联
            articleTagService.saveBatch(articleTags);
            return ResponseResult.okResult();
        }

    @Override
    public ResponseResult getAllList(Integer pageNum, Integer pageSize, ArticleDto article) {
        //分页查询
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //条件查询
        queryWrapper.like(Objects.nonNull(article.getSummary()), Article::getSummary, "%"+article.getSummary()+"%")
                .like(Objects.nonNull(article.getTitle()), Article::getTitle, "%"+article.getTitle()+"%")
                .eq(Objects.nonNull(article.getCategoryId()), Article::getCategoryId, article.getCategoryId())
                .eq(Objects.nonNull(article.getStatus()), Article::getStatus, article.getStatus());

        Page<Article> page=new Page<>(pageNum==null?1:pageNum,pageSize==null?10:pageSize);
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();

        //TODO 后台查询文章
        //将文章中的路径加上域名（数据库中存储文件相对路径）
        /**
         *                                 /2023/12/30/2/2eacc66309ade14644a6b9b4f9bbf0ee/2eacc66309ade14644a6b9b4f9bbf0ee.csv
         * http://s66bj54ml.bkt.clouddn.com/2023/12/30/2/2eacc66309ade14644a6b9b4f9bbf0ee/2eacc66309ade14644a6b9b4f9bbf0ee.csv
         **/
        List<Article> articleList = articles.stream().map(s -> {
            // 处理缩略图URL，如果为null或空，则保持null
            if (s.getThumbnail() != null && !s.getThumbnail().trim().isEmpty()) {
                // 如果已经是完整URL（包含CDN），则直接使用；否则拼接CDN
                if (s.getThumbnail().startsWith("http://") || s.getThumbnail().startsWith("https://")) {
                    return s;
                } else {
                    s.setThumbnail(CDN + s.getThumbnail());
                }
            }
            return s;
        }).collect(Collectors.toList());
        //转换为VO
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);
        //设置分类名称
        articleListVos.stream().forEach(vo -> {
            // 从原始Article对象中获取categoryId
            Article article_inner = articleList.stream()
                    .filter(a -> a.getId().equals(vo.getId()))
                    .findFirst()
                    .orElse(null);
            if (article_inner != null && article_inner.getCategoryId() != null) {
                Category category = categoryService.getById(article_inner.getCategoryId());
                if (category != null) {
                    vo.setCategoryName(category.getName());
                }
            }
        });
        //封装查询结果
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 修改文章
     * @param articleDto
     * @return
     */
    @Override
    public ResponseResult updateArticle(ArticleDto articleDto) {
        // 先查询原文章，保留viewCount等系统管理的字段
        Article existingArticle = null;
        if (articleDto.getId() != null) {
            existingArticle = getById(articleDto.getId());
        }
        
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        
        // 保留原有的viewCount（访问量由系统自动管理，不应被前端修改）
        if (existingArticle != null && existingArticle.getViewCount() != null) {
            article.setViewCount(existingArticle.getViewCount());
        } else if (article.getViewCount() == null) {
            article.setViewCount(0L);
        }
        
        // 处理缩略图URL，去掉CDN域名（数据库中存储相对路径）
        String thumbnailUrl = article.getThumbnail();
        if (thumbnailUrl != null && !thumbnailUrl.trim().isEmpty()) {
            // 如果包含CDN域名，则去掉域名，只保留相对路径
            if (thumbnailUrl.contains(CDN)) {
                String filepath = thumbnailUrl.replace(CDN, "");
                article.setThumbnail(filepath);
            }
            // 如果不包含CDN域名，说明已经是相对路径，直接使用
        } else {
            // 如果缩略图为空，设置为null
            article.setThumbnail(null);
        }
        
        baseMapper.updateById(article);

        return ResponseResult.okResult();
    }


    @Override
    public ResponseResult deleteArticle(Long id) {
        baseMapper.deleteById(id);
        return ResponseResult.okResult();
    }
}
