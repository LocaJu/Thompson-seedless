package com.seed.controller;

import com.seed.domain.ResponseResult;
import com.seed.domain.dto.TagDTO;
import com.seed.domain.vo.PageVo;
import com.seed.domain.vo.TagVo;
import com.seed.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagDTO tagDTO){

        return tagService.pageTagList(pageNum, pageSize, tagDTO);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagDTO tagDTO){

        return tagService.addTag(tagDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTagById(@PathVariable("id") Long id){
        return tagService.deleteTagById(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getTagById(@PathVariable("id") Long id){
        return tagService.getTagById(id);
    }

    @PutMapping
    public ResponseResult updateTag(@RequestBody TagDTO tagDTO){
        return tagService.updateTag(tagDTO);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }
}
