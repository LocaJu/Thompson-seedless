package com.seed.ai.deepseek;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seed.exception.ServiceException;
import com.seed.ruoyi.utils.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DeepSeek Chat Completions 客户端（OpenAI 兼容接口）
 */
@Component
public class DeepSeekClient {

    private final RestTemplate deepSeekRestTemplate;
    private final DeepSeekProperties properties;

    public DeepSeekClient(RestTemplate deepSeekRestTemplate, DeepSeekProperties properties) {
        this.deepSeekRestTemplate = deepSeekRestTemplate;
        this.properties = properties;
    }

    public String chat(List<DeepSeekMessage> messages) {
        if (properties == null || !StringUtils.hasText(properties.getBaseUrl())) {
            throw new ServiceException("DeepSeek base-url 未配置");
        }
        if (!StringUtils.hasText(properties.getApiKey())) {
            throw new ServiceException("DeepSeek api-key 未配置，请设置环境变量 DEEPSEEK_API_KEY");
        }
        if (messages == null || messages.isEmpty()) {
            throw new ServiceException("messages 不能为空");
        }

        String url = properties.getBaseUrl();
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        url = url + "/chat/completions";

        Map<String, Object> body = new HashMap<>();
        body.put("model", StringUtils.hasText(properties.getModel()) ? properties.getModel() : "deepseek-chat");
        body.put("messages", messages);
        body.put("temperature", properties.getTemperature() == null ? 0.7 : properties.getTemperature());
        body.put("stream", false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(java.util.Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(properties.getApiKey());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response;
        try {
            response = deepSeekRestTemplate.postForEntity(url, entity, String.class);
        } catch (HttpStatusCodeException e) {
            // 4xx/5xx 会走这里，尽量解析出可读错误
            String readable = toReadableDeepSeekError(e.getStatusCode().value(), e.getResponseBodyAsString());
            throw new ServiceException(readable);
        } catch (RestClientException e) {
            throw new ServiceException("调用 DeepSeek 失败：" + e.getMessage());
        }

        if (response == null) {
            throw new ServiceException("DeepSeek 响应为空");
        }
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServiceException("DeepSeek 响应异常，http=" + response.getStatusCode().value());
        }

        String respBody = response.getBody();
        if (!StringUtils.hasText(respBody)) {
            throw new ServiceException("DeepSeek 响应 body 为空");
        }

        try {
            JSONObject obj = JSON.parseObject(respBody);
            JSONArray choices = obj.getJSONArray("choices");
            if (choices == null || choices.isEmpty()) {
                throw new ServiceException("DeepSeek 响应缺少 choices");
            }
            JSONObject choice0 = choices.getJSONObject(0);
            JSONObject message = choice0.getJSONObject("message");
            if (message == null) {
                throw new ServiceException("DeepSeek 响应缺少 message");
            }
            String content = message.getString("content");
            if (!StringUtils.hasText(content)) {
                throw new ServiceException("DeepSeek 返回内容为空");
            }
            return content;
        } catch (Exception e) {
            throw new ServiceException("解析 DeepSeek 响应失败：" + e.getMessage());
        }
    }

    private String toReadableDeepSeekError(int httpStatus, String responseBody) {
        // DeepSeek 常见错误体：
        // {"error":{"message":"Insufficient Balance","type":"...","param":null,"code":"invalid_request_error"}}
        if (httpStatus == 402) {
            String msg = extractErrorMessage(responseBody);
            return "DeepSeek 余额不足（402）" + (StringUtils.hasText(msg) ? ("：" + msg) : "");
        }
        if (httpStatus == 401) {
            String msg = extractErrorMessage(responseBody);
            return "DeepSeek 鉴权失败（401）" + (StringUtils.hasText(msg) ? ("：" + msg) : "");
        }
        String msg = extractErrorMessage(responseBody);
        if (StringUtils.hasText(msg)) {
            return "调用 DeepSeek 失败：" + msg;
        }
        return "调用 DeepSeek 失败：http=" + httpStatus;
    }

    private String extractErrorMessage(String responseBody) {
        if (!StringUtils.hasText(responseBody)) {
            return null;
        }
        try {
            JSONObject obj = JSON.parseObject(responseBody);
            if (obj == null) {
                return null;
            }
            JSONObject err = obj.getJSONObject("error");
            if (err == null) {
                return null;
            }
            return err.getString("message");
        } catch (Exception ignore) {
            return null;
        }
    }
}

