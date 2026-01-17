package com.seed.service.ai.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seed.ai.deepseek.DeepSeekClient;
import com.seed.ai.deepseek.DeepSeekMessage;
import com.seed.domain.ResponseResult;
import com.seed.domain.dto.ai.AiTalkRequest;
import com.seed.domain.entity.ai.AiChatMessage;
import com.seed.domain.entity.ai.AiChatSession;
import com.seed.domain.vo.ai.AiChatMessageVo;
import com.seed.domain.vo.ai.AiChatSessionVo;
import com.seed.domain.vo.ai.AiTalkResponse;
import com.seed.exception.ServiceException;
import com.seed.mapper.ai.AiChatMessageMapper;
import com.seed.mapper.ai.AiChatSessionMapper;
import com.seed.ruoyi.utils.StringUtils;
import com.seed.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AiChatServiceImpl implements com.seed.service.ai.AiChatService {

    private static final int CONTEXT_MESSAGE_LIMIT = 20;
    private static final String DEFAULT_SYSTEM_PROMPT = "你是一个友好、专业的博客站点AI助手。请用简洁中文回答用户问题。";

    private final AiChatSessionMapper sessionMapper;
    private final AiChatMessageMapper messageMapper;
    private final DeepSeekClient deepSeekClient;

    public AiChatServiceImpl(AiChatSessionMapper sessionMapper,
                             AiChatMessageMapper messageMapper,
                             DeepSeekClient deepSeekClient) {
        this.sessionMapper = sessionMapper;
        this.messageMapper = messageMapper;
        this.deepSeekClient = deepSeekClient;
    }

    @Override
    public ResponseResult<?> talk(AiTalkRequest request) {
        if (request == null || !StringUtils.hasText(request.getContent())) {
            throw new ServiceException("请输入内容");
        }
        Long userId = SecurityUtils.getUserId();

        AiChatSession session = ensureSession(userId, request.getSessionId(), request.getContent());

        // 组装多轮上下文
        List<DeepSeekMessage> messages = new ArrayList<>();
        messages.add(new DeepSeekMessage("system", DEFAULT_SYSTEM_PROMPT));

        List<AiChatMessage> history = latestMessagesForContext(userId, session.getId());
        for (AiChatMessage m : history) {
            if (!StringUtils.hasText(m.getRole()) || !StringUtils.hasText(m.getContent())) {
                continue;
            }
            messages.add(new DeepSeekMessage(m.getRole(), m.getContent()));
        }
        messages.add(new DeepSeekMessage("user", request.getContent()));

        // 先保存用户消息
        AiChatMessage userMsg = new AiChatMessage();
        userMsg.setSessionId(session.getId());
        userMsg.setUserId(userId);
        userMsg.setRole("user");
        userMsg.setContent(request.getContent());
        userMsg.setDelFlag(0);
        messageMapper.insert(userMsg);

        // 调用 DeepSeek 获取回复
        String reply = deepSeekClient.chat(messages);

        // 保存机器人消息
        AiChatMessage assistantMsg = new AiChatMessage();
        assistantMsg.setSessionId(session.getId());
        assistantMsg.setUserId(userId);
        assistantMsg.setRole("assistant");
        assistantMsg.setContent(reply);
        assistantMsg.setDelFlag(0);
        messageMapper.insert(assistantMsg);

        // 更新会话最近时间
        AiChatSession upd = new AiChatSession();
        upd.setId(session.getId());
        upd.setLastMessageTime(new Date());
        sessionMapper.updateById(upd);

        AiTalkResponse resp = new AiTalkResponse();
        resp.setSessionId(session.getId());
        resp.setUserMessage(toVo(userMsg));
        resp.setAssistantMessage(toVo(assistantMsg));
        return ResponseResult.okResult(resp);
    }

    @Override
    public ResponseResult<?> listSessions() {
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<AiChatSession> qw = new LambdaQueryWrapper<>();
        qw.eq(AiChatSession::getUserId, userId)
                .eq(AiChatSession::getDelFlag, 0)
                .orderByDesc(AiChatSession::getLastMessageTime)
                .orderByDesc(AiChatSession::getCreateTime);
        List<AiChatSession> sessions = sessionMapper.selectList(qw);
        List<AiChatSessionVo> vos = new ArrayList<>();
        if (sessions != null) {
            for (AiChatSession s : sessions) {
                AiChatSessionVo vo = new AiChatSessionVo();
                vo.setId(s.getId());
                vo.setTitle(s.getTitle());
                vo.setLastMessageTime(s.getLastMessageTime());
                vo.setCreateTime(s.getCreateTime());
                vos.add(vo);
            }
        }
        return ResponseResult.okResult(vos);
    }

    @Override
    public ResponseResult<?> listMessages(Long sessionId) {
        if (sessionId == null) {
            throw new ServiceException("sessionId 不能为空");
        }
        Long userId = SecurityUtils.getUserId();
        assertSessionOwner(userId, sessionId);

        LambdaQueryWrapper<AiChatMessage> qw = new LambdaQueryWrapper<>();
        qw.eq(AiChatMessage::getSessionId, sessionId)
                .eq(AiChatMessage::getUserId, userId)
                .eq(AiChatMessage::getDelFlag, 0)
                .orderByAsc(AiChatMessage::getId);
        List<AiChatMessage> list = messageMapper.selectList(qw);
        List<AiChatMessageVo> vos = new ArrayList<>();
        if (list != null) {
            for (AiChatMessage m : list) {
                vos.add(toVo(m));
            }
        }
        return ResponseResult.okResult(vos);
    }

    private AiChatSession ensureSession(Long userId, Long sessionId, String firstUserContent) {
        if (sessionId != null) {
            assertSessionOwner(userId, sessionId);
            AiChatSession s = sessionMapper.selectById(sessionId);
            if (s == null || s.getDelFlag() != null && s.getDelFlag() == 1) {
                throw new ServiceException("会话不存在");
            }
            return s;
        }

        AiChatSession s = new AiChatSession();
        s.setUserId(userId);
        s.setTitle(genTitle(firstUserContent));
        s.setLastMessageTime(new Date());
        s.setDelFlag(0);
        sessionMapper.insert(s);
        return s;
    }

    private void assertSessionOwner(Long userId, Long sessionId) {
        AiChatSession s = sessionMapper.selectById(sessionId);
        if (s == null || s.getDelFlag() != null && s.getDelFlag() == 1) {
            throw new ServiceException("会话不存在");
        }
        if (s.getUserId() == null || !s.getUserId().equals(userId)) {
            throw new ServiceException("无权访问该会话");
        }
    }

    private List<AiChatMessage> latestMessagesForContext(Long userId, Long sessionId) {
        // 先取最近 N 条，再反转为时间正序
        LambdaQueryWrapper<AiChatMessage> qw = new LambdaQueryWrapper<>();
        qw.eq(AiChatMessage::getSessionId, sessionId)
                .eq(AiChatMessage::getUserId, userId)
                .eq(AiChatMessage::getDelFlag, 0)
                .orderByDesc(AiChatMessage::getId)
                .last("limit " + CONTEXT_MESSAGE_LIMIT);
        List<AiChatMessage> latest = messageMapper.selectList(qw);
        if (latest == null || latest.isEmpty()) {
            return new ArrayList<>();
        }
        List<AiChatMessage> asc = new ArrayList<>();
        for (int i = latest.size() - 1; i >= 0; i--) {
            asc.add(latest.get(i));
        }
        return asc;
    }

    private String genTitle(String content) {
        String c = content == null ? "" : content.trim();
        if (c.length() <= 20) {
            return c;
        }
        return c.substring(0, 20) + "...";
    }

    private AiChatMessageVo toVo(AiChatMessage m) {
        AiChatMessageVo vo = new AiChatMessageVo();
        vo.setId(m.getId());
        vo.setSessionId(m.getSessionId());
        vo.setRole(m.getRole());
        vo.setContent(m.getContent());
        vo.setCreateTime(m.getCreateTime());
        return vo;
    }
}

