package com.seed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.constants.SystemConstants;
import com.seed.domain.ResponseResult;
import com.seed.domain.entity.Comment;
import com.seed.domain.vo.CommentVo;
import com.seed.domain.vo.PageVo;
import com.seed.enums.AppHttpCodeEnum;
import com.seed.exception.SystemException;
import com.seed.service.CommentService;
import com.seed.mapper.CommentMapper;

import com.seed.service.system.ISysUserService;
import com.seed.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 77286
* @description 针对表【tb_comment(评论表)】的数据库操作Service实现
* @createDate 2023-12-16 21:59:44
*/
@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
implements CommentService{

    @Autowired
     private ISysUserService userService;
    @Override
    public ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论

        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        //对articleId进行处理(如果是文章类型评论才进行ArticleId判断)
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //根评论
        queryWrapper.eq(Comment::getRootId,-1);
        //评论类型
        queryWrapper.eq(Comment::getType,commentType);
        //分页查询
        Page<Comment> page=new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        //转换为VO
        List<CommentVo> commentVos = toCommentVoList(page.getRecords());

        //根据根评论的id查询其子评论的集合 并对其相应的属性赋值
        for (CommentVo commentVo : commentVos) {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        }

        //根据子评论的个数降序排列
        commentVos = commentVos.stream().sorted(new Comparator<CommentVo>() {
            @Override
            public int compare(CommentVo o1, CommentVo o2) {
                int size1 = o1.getChildren().size();
                int size2 = o2.getChildren().size();
                if (size1 < size2) {
                    return 1;
                } else if (size1 > size2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }).collect(Collectors.toList());


        return ResponseResult.okResult(new PageVo(commentVos,page.getTotal()));
    }
    /**
     * @description: 根据根评论的id查询其子评论的集合
     * @author: 77286
     * @date: 2023/12/17 22:27
     * @param: Long id
     * @return: java.util.List<com.seed.domain.vo.CommentVo>
     * @version 1.0
     **/
    private List<CommentVo> getChildren(Long id){
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;


    }
    /**
     * @description: 为其相应的属性赋值
     * @author: 77286
     * @date: 2023/12/17 22:28
     * @param: Comment> list
     * @return: java.util.List<com.seed.domain.vo.CommentVo>
     * @version 1.0
     **/
    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);

        //遍历Vo集合
        for (CommentVo commentVo : commentVos) {
            //通过creatBy查询用户的昵称并赋值
            String nickName = userService.selectUserById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);

            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if (commentVo.getToCommentUserId()!=-1){
                String toCommentUserName = userService.selectUserById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }

        return commentVos;
    }

    //添加评论
    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }
}
