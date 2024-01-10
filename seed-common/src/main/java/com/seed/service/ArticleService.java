package com.seed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seed.domain.ResponseResult;
import com.seed.domain.entity.Article;

/**
* @author 77286
* @description 针对表【tb_article(文章表)】的数据库操作Service
* @createDate 2023-12-10 16:18:36
*/
public interface ArticleService extends IService<Article> {

     ResponseResult getArticleDetail(Long id);


    ResponseResult getList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult updateViewCount(Long id);
}
