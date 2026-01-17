package com.seed.domain.entity.ai;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * AI 聊天会话表
 * @TableName tb_ai_chat_session
 */
@TableName(value = "tb_ai_chat_session")
@Data
public class AiChatSession implements Serializable {

    @TableId
    private Long id;

    /**
     * 归属用户ID（sys_user.user_id）
     */
    private Long userId;

    /**
     * 会话标题
     */
    private String title;

    /**
     * 最近一条消息时间
     */
    private Date lastMessageTime;

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

