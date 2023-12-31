package com.seed.controller;

import com.seed.domain.LoginUser;
import com.seed.domain.ResponseResult;
import com.seed.domain.entity.User;
import com.seed.domain.vo.BlogUserInfoVo;
import com.seed.domain.vo.UserInfoVo;
import com.seed.enums.AppHttpCodeEnum;
import com.seed.exception.SystemException;
import com.seed.service.BlogLoginService;
import com.seed.utils.BeanCopyUtils;
import com.seed.utils.JwtUtil;
import com.seed.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/17 14:45
 */
@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @RequestMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
