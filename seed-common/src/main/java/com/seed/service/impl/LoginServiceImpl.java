package com.seed.service.impl;

import com.seed.constants.SystemConstants;
import com.seed.domain.LoginUser;
import com.seed.domain.ResponseResult;
import com.seed.domain.entity.User;
import com.seed.domain.vo.BlogUserInfoVo;
import com.seed.domain.vo.UserInfoVo;
import com.seed.service.LoginService;
import com.seed.utils.BeanCopyUtils;
import com.seed.utils.JwtUtil;
import com.seed.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.seed.utils.RedisConstants.LOGIN;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2024/1/10 20:49
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userId生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject(LOGIN +userId,loginUser);
        //把token和userInfo封装 返回
        //将User转换为UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserInfoVo blogUserInfoVo=new BlogUserInfoVo(jwt,userInfoVo);

        return ResponseResult.okResult(blogUserInfoVo);
    }

    @Override
    public ResponseResult logout() {
        //获取token 解析获取userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        //获取userId
        Long userId = loginUser.getUser().getId();
        redisCache.deleteObject(LOGIN+userId);
        return ResponseResult.okResult();
    }
}
