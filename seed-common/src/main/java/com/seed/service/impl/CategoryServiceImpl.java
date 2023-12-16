package com.seed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.constants.SystemConstants;
import com.seed.domain.ResponseResult;
import com.seed.domain.entity.Article;
import com.seed.domain.entity.Category;
import com.seed.domain.vo.CategoryVo;
import com.seed.mapper.CategoryMapper;
import com.seed.service.ArticleService;
import com.seed.service.CategoryService;
import com.seed.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 77286
* @description 针对表【tb_category(分类表)】的数据库操作Service实现
* @createDate 2023-12-10 20:30:40
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
implements CategoryService {

    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //查询文章表 状态为已发布的文章
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleLambdaQueryWrapper);
        //获取文章的分类id  并去重
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategory_id)
                .collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categories);
    }
}
