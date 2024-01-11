package com.seed.controller;

import com.seed.domain.ResponseResult;
import com.seed.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2024/1/5 21:25
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/list")
    public ResponseResult list(){
        return ResponseResult.okResult(tagService.list());
    }

}
