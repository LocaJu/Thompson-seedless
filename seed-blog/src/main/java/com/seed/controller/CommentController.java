package com.seed.controller;

import com.seed.constants.SystemConstants;
import com.seed.domain.ResponseResult;
import com.seed.domain.entity.Comment;
import com.seed.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/17 20:32
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping()
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}
