package com.seed.domain.vo.ai;

import lombok.Data;

import java.util.Date;

@Data
public class AiChatMessageVo {
    private Long id;
    private Long sessionId;
    private String role;
    private String content;
    private Date createTime;
}

