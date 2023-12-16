package com.seed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.seed.constants.SystemConstants;
import com.seed.domain.ResponseResult;
import com.seed.domain.entity.Article;
import com.seed.domain.vo.ArticleListVo;
import com.seed.domain.vo.HotArticleVo;
import com.seed.domain.vo.PageVo;
import com.seed.mapper.ArticleMapper;
import com.seed.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.seed.service.ArticleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.seed.constants.SystemConstants.ARTICLE_STATUS_NORMAL;

/**
* @author 77286
* @description 针对表【tb_article(文章表)】的数据库操作Service实现
* @createDate 2023-12-10 16:18:36
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService{
    @Override
    public ResponseResult getList() {
        //查询热门文章
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        //正式文章  （非正在编辑文章）
        queryWrapper.eq(Article::getStatus,ARTICLE_STATUS_NORMAL);
        //按照浏览进行排序
        queryWrapper.orderByDesc(Article::getView_count);
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
        articleLambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategory_id,categoryId);

        //状态是否时正式发布（非编辑状态）
        articleLambdaQueryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        //根据istop优先级（是否置顶）排序
        articleLambdaQueryWrapper.orderByDesc(Article::getIs_top);
        //分页查询
        Page<Article> articlePage=new Page<>(pageNum==null?1:pageNum,pageSize==null?10:pageSize);
        page(articlePage,articleLambdaQueryWrapper);

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articlePage.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, articlePage.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
