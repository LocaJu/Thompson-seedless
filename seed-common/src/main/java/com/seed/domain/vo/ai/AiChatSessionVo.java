package com.seed.domain.vo.ai;

import lombok.Data;

import java.util.Date;

@Data
public class AiChatSessionVo {
    private Long id;
    private String title;
    private Date lastMessageTime;
    private Date createTime;
}

