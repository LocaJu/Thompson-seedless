package com.seed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.domain.entity.Category;
import com.seed.mapper.CategoryMapper;
import com.seed.service.CategoryService;
import org.springframework.stereotype.Service;

/**
* @author 77286
* @description 针对表【tb_category(分类表)】的数据库操作Service实现
* @createDate 2023-12-10 20:30:40
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
implements CategoryService {

}
