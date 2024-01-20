package com.seed.filter;

import com.alibaba.fastjson.JSON;

import com.seed.domain.ResponseResult;
import com.seed.domain.model.LoginUser;
import com.seed.enums.AppHttpCodeEnum;
import com.seed.service.system.web.service.TokenService;
//import com.seed.utils.JwtUtil;
import com.seed.utils.RedisCache;
import com.seed.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

import static com.seed.utils.RedisConstants.BLOG_LOGIN;

/**
 * @author 77286
 * @version 1.0
 * @description: 校验请求token
 * @date 2023/12/17 16:28
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)){
            //没有携带token    不进行token验证
            filterChain.doFilter(request,response);
            return;
        }
        //解析token获取userId
//        Claims claims=null;
        String uuid=null;
        try {
            uuid = tokenService.getUsernameFromToken(token);
//            claims = JwtUtil.parseJWT(token);
        }catch (Exception e){
            e.printStackTrace();
            //token超时||token非法
            //响应告诉前端需要从新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
//        String userId = claims.getSubject();
        //从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject(BLOG_LOGIN + uuid);
        //如果redis中获取不到
        if (Objects.isNull(loginUser)){
            //说明登录过期
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //存入SecurityContentHolder
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
