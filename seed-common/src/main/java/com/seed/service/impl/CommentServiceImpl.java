package com.seed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.domain.entity.Comment;
import com.seed.service.CommentService;
import com.seed.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author 77286
* @description 针对表【tb_comment(评论表)】的数据库操作Service实现
* @createDate 2023-12-16 21:59:44
*/
@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
implements CommentService{

}
