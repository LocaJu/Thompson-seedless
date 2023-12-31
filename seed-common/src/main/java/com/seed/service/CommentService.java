package com.seed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seed.domain.ResponseResult;
import com.seed.domain.entity.Comment;


/**
* @author 77286
* @description 针对表【tb_comment(评论表)】的数据库操作Service
* @createDate 2023-12-16 21:59:44
*/
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String type ,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
