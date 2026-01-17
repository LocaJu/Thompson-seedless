package com.seed.service.ai;

import com.seed.domain.ResponseResult;
import com.seed.domain.dto.ai.AiTalkRequest;

public interface AiChatService {

    ResponseResult<?> talk(AiTalkRequest request);

    ResponseResult<?> listSessions();

    ResponseResult<?> listMessages(Long sessionId);
}

