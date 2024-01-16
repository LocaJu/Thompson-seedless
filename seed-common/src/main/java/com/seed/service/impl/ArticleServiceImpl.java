package com.seed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.domain.ResponseResult;
import com.seed.domain.dto.AddArticleDto;
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
    RedisCache redisCache;

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
        article.setViewCount(viewCount.longValue());
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
        public ResponseResult add(AddArticleDto articleDto) {
            //添加 博客
            Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
            /**
            * 保存图片的相对路径----->查询时加上域名
            *                 http://s66bj54ml.bkt.clouddn.com/2023/12/30/2/2eacc66309ade14644a6b9b4f9bbf0ee/2eacc66309ade14644a6b9b4f9bbf0ee.csv
            *得到文件路径 如：                                   /2023/12/30/2/2eacc66309ade14644a6b9b4f9bbf0ee/2eacc66309ade14644a6b9b4f9bbf0ee.csv
            */
            //将文章中的路径域名去掉（数据库中存储文件相对路径）
            String thumbnailUrl = article.getThumbnail();
            String filepath = thumbnailUrl.replace(CDN, "");
            article.setThumbnail(filepath);

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
    public ResponseResult getAllList() {
        List<Article> articles = baseMapper.selectList(null);
        //TODO 后台查询文章
        //将文章中的路径加上域名（数据库中存储文件相对路径）
        /**
         *                                 /2023/12/30/2/2eacc66309ade14644a6b9b4f9bbf0ee/2eacc66309ade14644a6b9b4f9bbf0ee.csv
         * http://s66bj54ml.bkt.clouddn.com/2023/12/30/2/2eacc66309ade14644a6b9b4f9bbf0ee/2eacc66309ade14644a6b9b4f9bbf0ee.csv
         **/
        List<Article> articleList = articles.stream().map(s -> s.setThumbnail(CDN + s.getThumbnail())).collect(Collectors.toList());
        //转换为VO
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);
        return ResponseResult.okResult(articleListVos);
    }
}
