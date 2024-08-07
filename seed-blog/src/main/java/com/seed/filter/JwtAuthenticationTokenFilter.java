package com.seed.filter;

import com.alibaba.fastjson.JSON;
import com.seed.domain.ResponseResult;
import com.seed.domain.model.LoginUser;
import com.seed.enums.AppHttpCodeEnum;
import com.seed.ruoyi.constant.Constants;
import com.seed.service.system.web.service.TokenService;
import com.seed.utils.RedisCache;
import com.seed.utils.RedisConstants;
import com.seed.utils.WebUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;



import java.io.IOException;
import java.util.Objects;


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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)){
            //没有携带token    不进行token验证
            filterChain.doFilter(request,response);
            return;
        }
        //解析token获取userId
        Claims claims=null;
        String uuid=null;
        try {

        claims = tokenService.parseToken(token);
        uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
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
        LoginUser loginUser = redisCache.getCacheObject(RedisConstants.LOGIN_TOKENS + uuid);
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
