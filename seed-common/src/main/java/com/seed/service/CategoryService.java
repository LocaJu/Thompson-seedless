package com.seed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seed.domain.ResponseResult;
import com.seed.domain.entity.Category;

/**
* @author 77286
* @description 针对表【tb_category(分类表)】的数据库操作Service
* @createDate 2023-12-10 20:30:40
*/
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
