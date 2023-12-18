package com.seed.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/17 20:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {
    /**
     *
     */
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 根评论id
     */
    private Long rootId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 所回复的目标评论的userid
     */
    private Long toCommentUserId;

    /**
     * 所回复的目标评论的名称
     **/
    private String toCommentUserName;
    /**
     * 回复目标评论id
     */
    private Long toCommentId;

    /**
     *
     */
    private Long createBy;

    /**
     *
     */
    private Date createTime;

    /**
     * 评论论人
     **/
    private String username;

    private List<CommentVo> children;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
