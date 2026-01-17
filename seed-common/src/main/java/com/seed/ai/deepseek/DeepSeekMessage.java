package com.seed.ai.deepseek;

/**
 * DeepSeek / OpenAI 风格 messages[] 元素
 */
public class DeepSeekMessage {
    private String role;
    private String content;

    public DeepSeekMessage() {
    }

    public DeepSeekMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

