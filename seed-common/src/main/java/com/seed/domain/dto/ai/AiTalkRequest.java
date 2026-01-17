package com.seed.domain.dto.ai;

import lombok.Data;

/**
 * 发送消息（多轮对话）
 */
@Data
public class AiTalkRequest {
    /**
     * 会话ID；为空则创建新会话
     */
    private Long sessionId;

    /**
     * 用户输入
     */
    private String content;
}

