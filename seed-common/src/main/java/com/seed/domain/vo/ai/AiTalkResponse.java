package com.seed.domain.vo.ai;

import lombok.Data;

@Data
public class AiTalkResponse {
    private Long sessionId;
    private AiChatMessageVo userMessage;
    private AiChatMessageVo assistantMessage;
}

