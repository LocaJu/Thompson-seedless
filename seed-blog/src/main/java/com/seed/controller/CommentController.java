package com.seed.controller;

import com.seed.constants.SystemConstants;
import com.seed.domain.ResponseResult;
import com.seed.domain.dto.AddCommentDTO;
import com.seed.domain.entity.Comment;
import com.seed.service.CommentService;
import com.seed.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "评论接口")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @ApiOperation(value = "获取评论列表",notes = "获取评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量", required = true, paramType = "query"),
            @ApiImplicitParam(name = "articleId", value = "文章ID", required = true, paramType = "query")
    })
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping
    @ApiOperation(value = "添加评论",notes = "添加评论")
    public ResponseResult addComment(@RequestBody AddCommentDTO addCommentDTO){
        Comment comment = BeanCopyUtils.copyBean(addCommentDTO, Comment.class);
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    @ApiOperation(value = "获取友链评论列表",notes = "获取友链评论列表")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}
