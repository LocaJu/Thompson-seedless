package com.seed.controller;

import com.seed.domain.ResponseResult;
import com.seed.domain.dto.CategoryDTO;
import com.seed.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2024/1/14 22:33
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有正常的分类
     * @return
     */
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        return categoryService.listAllCategory();
    }

    /**
     * 分页获取分类列表
     * @param pageNum
     * @param pageSize
     * @param category
     * @return
     */
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, CategoryDTO category) {
        return categoryService.getList(pageNum, pageSize, category);
    }

    /**
     * 根据id获取分类
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult getCategoryDetailById(@PathVariable("id") Long id) {
        return categoryService.getCategoryDetailById(id);
    }

    /**
     * 更新分类
     * @param category
     * @return
     */
    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryDTO category) {
        return categoryService.updateCategory(category);
    }
}
