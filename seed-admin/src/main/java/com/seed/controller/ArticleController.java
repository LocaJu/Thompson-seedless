package com.seed.controller;

import com.seed.domain.ResponseResult;
import com.seed.domain.dto.AddArticleDto;
import com.seed.domain.entity.Article;
import com.seed.domain.vo.ArticleListVo;
import com.seed.service.ArticleService;
import com.seed.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("/list")
    public ResponseResult list(){
        return articleService.getAllList();
    }
}
