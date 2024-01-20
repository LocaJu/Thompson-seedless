package com.seed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.constants.SystemConstants;
import com.seed.domain.ResponseResult;
import com.seed.domain.dto.CategoryDTO;
import com.seed.domain.entity.Article;
import com.seed.domain.entity.Category;
import com.seed.domain.vo.CategoryVo;
import com.seed.domain.vo.PageVo;
import com.seed.mapper.CategoryMapper;
import com.seed.service.ArticleService;
import com.seed.service.CategoryService;
import com.seed.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 77286
* @description 针对表【tb_category(分类表)】的数据库操作Service实现
* @createDate 2023-12-10 20:30:40
*/
@Service
@Slf4j
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
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list,
                CategoryVo.class);
        return ResponseResult.okResult(categoryVos);

    }

    /**
     * 管理端查询分类列表
     * @param pageNum
     * @param pageSize
     * @param category
     * @return
     */
    @Override
    public ResponseResult getList(Integer pageNum, Integer pageSize, CategoryDTO category) {
        //查询分类表
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Objects.nonNull(category.getName()),Category::getName, category.getName())
                .eq(Objects.nonNull(category.getStatus()),Category::getStatus, category.getStatus());
        //分页查询
        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);

        List<Category> records = page.getRecords();
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(records, CategoryVo.class);
        PageVo pageVo = new PageVo(categoryVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     *  * 根据id查询分类详情
     * @param id
     * @return
     */
    @Override
    public ResponseResult getCategoryDetailById(Long id) {
        Category category = baseMapper.selectById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    /**
     *  * 更新分类明细
     * @param categoryDto
     * @return
     */
    @Override
    public ResponseResult updateCategory(CategoryDTO categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        baseMapper.updateById(category);
        return ResponseResult.okResult();
    }
}
