package com.seed.ai.deepseek;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * DeepSeek API 配置
 */
@ConfigurationProperties(prefix = "deepseek")
public class DeepSeekProperties {

    /**
     * 例如：https://api.deepseek.com
     */
    private String baseUrl;

    /**
     * 例如：sk-xxxx （建议使用环境变量注入）
     */
    private String apiKey;

    /**
     * 例如：deepseek-chat
     */
    private String model = "deepseek-chat";

    /**
     * HTTP 超时（毫秒）
     */
    private Integer timeoutMs = 15000;

    /**
     * 温度
     */
    private Double temperature = 0.7;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getTimeoutMs() {
        return timeoutMs;
    }

    public void setTimeoutMs(Integer timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}

