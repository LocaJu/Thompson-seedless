package com.seed.service.impl;


import com.seed.domain.ResponseResult;

import com.seed.domain.entity.system.SysUser;
import com.seed.domain.model.LoginUser;
import com.seed.domain.vo.BlogUserInfoVo;
import com.seed.domain.vo.UserInfoVo;
import com.seed.service.BlogLoginService;
import com.seed.service.system.security.context.AuthenticationContextHolder;
import com.seed.service.system.web.service.TokenService;
import com.seed.utils.BeanCopyUtils;

import com.seed.utils.RedisCache;
import com.seed.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.seed.utils.RedisConstants.BLOG_LOGIN;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/17 16:05
 */
@Service
@Slf4j
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TokenService tokenService;

    @Override
    public ResponseResult login(SysUser user) {

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        AuthenticationContextHolder.setContext(authenticationToken);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userId生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
//        String token = JwtUtil.createJWT(userId);
        String token = tokenService.createToken(loginUser);

        //把用户信息存入redis
        redisCache.setCacheObject(BLOG_LOGIN+userId,loginUser);
        //把token和userInfo封装 返回
        //将User转换为UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserInfoVo blogUserInfoVo=new BlogUserInfoVo(token,userInfoVo);

        return ResponseResult.okResult(blogUserInfoVo);
    }

    @Override
    public ResponseResult logout() {
        //获取token 解析获取userId
        Authentication authentication = SecurityUtils.getAuthentication();
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        //获取userId
        Long userId = loginUser.getUser().getUserId();
        redisCache.deleteObject(BLOG_LOGIN+userId);
        return ResponseResult.okResult();
    }
}
