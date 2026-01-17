package com.seed.controller;

import com.seed.domain.ResponseResult;
import com.seed.domain.dto.ai.AiTalkRequest;
import com.seed.service.ai.AiChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/chat")
@Api(tags = "AI聊天接口")
public class AiChatController {

    @Autowired
    private AiChatService aiChatService;

    @PostMapping("/talk")
    @ApiOperation(value = "对话（支持多轮上下文，自动保存历史）")
    public ResponseResult<?> talk(@RequestBody AiTalkRequest request) {
        return aiChatService.talk(request);
    }

    @GetMapping("/sessions")
    @ApiOperation(value = "会话列表")
    public ResponseResult<?> sessions() {
        return aiChatService.listSessions();
    }

    @GetMapping("/sessions/{sessionId}/messages")
    @ApiOperation(value = "会话消息历史")
    public ResponseResult<?> messages(@PathVariable("sessionId") Long sessionId) {
        return aiChatService.listMessages(sessionId);
    }
}

