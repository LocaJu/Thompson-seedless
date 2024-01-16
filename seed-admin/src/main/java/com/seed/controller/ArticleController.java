package com.seed.controller;

import com.seed.domain.ResponseResult;
import com.seed.domain.dto.ArticleDto;
import com.seed.domain.vo.PageVo;
import com.seed.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2024/1/14 22:53
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    /**
     * 添加文章
     * @param article
     * @return
     */
    @PostMapping
    public ResponseResult add(@RequestBody ArticleDto article){
        return articleService.add(article);
    }

    @PostMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize,ArticleDto article){
        return articleService.getAllList(pageNum, pageSize, article);
    }

    @GetMapping("{id}")
    public ResponseResult getArticleById(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody ArticleDto article){
        return articleService.updateArticle(article);
    }
}
