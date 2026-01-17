package com.seed.domain.entity.ai;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * AI 聊天消息表
 * @TableName tb_ai_chat_message
 */
@TableName(value = "tb_ai_chat_message")
@Data
public class AiChatMessage implements Serializable {

    @TableId
    private Long id;

    private Long sessionId;

    /**
     * 归属用户ID（方便查询/鉴权）
     */
    private Long userId;

    /**
     * user / assistant / system
     */
    private String role;

    /**
     * 文本内容
     */
    private String content;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 删除标志（0未删除，1已删除）
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

