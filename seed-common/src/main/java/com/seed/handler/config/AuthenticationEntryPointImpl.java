package com.seed.handler.config;

import com.alibaba.fastjson.JSON;
import com.seed.domain.ResponseResult;
import com.seed.enums.AppHttpCodeEnum;
import com.seed.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/17 17:25
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();

        ResponseResult result = null;
        if (e instanceof BadCredentialsException) {
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), e.getMessage());
        } else if (e instanceof InsufficientAuthenticationException) {
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        } else {
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "认证或授权失败");
        }
        //响应给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
