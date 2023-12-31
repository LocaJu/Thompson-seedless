package com.seed.controller;

import com.seed.domain.ResponseResult;
import com.seed.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 77286
 * @version 1.0
 * @description: 文章管理
 * @date 2023/12/10 17:34
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }


    @GetMapping("/hotArticleList")
    public ResponseResult getList() {
        return articleService.getList();
    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

}
