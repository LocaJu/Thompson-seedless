package com.seed.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2024/1/5 20:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "添加评论")
public class AddCommentDTO {
    /**
     * 评论id
     */
    private Long id;

    /**
     * 评论类型（0代表文章评论，1代表友链评论）
     */
    @ApiModelProperty(value = "评论类型（0代表文章评论，1代表友链评论）")
    private String type;

    /**
     * 文章id
     */
    @ApiModelProperty(value = "文章id")
    private Long articleId;

    /**
     * 根评论id
     */
    @ApiModelProperty(value = "根评论id")
    private Long rootId;

    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容")
    private String content;

    /**
     * 所回复的目标评论的userid
     */
    @ApiModelProperty(value = "所回复的目标评论的userid")
    private Long toCommentUserId;

    /**
     * 回复目标评论id
     */
    @ApiModelProperty(value = "回复目标评论id")
    private Long toCommentId;

    /**
     *
     */
    @ApiModelProperty(value = "创建人")
    private Long createBy;

    /**
     *
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     *
     */
    @ApiModelProperty(value = "修改人")
    private Long updateBy;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删除）")
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
