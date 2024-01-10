package com.seed.controller;

import com.seed.domain.ResponseResult;
import com.seed.domain.entity.User;
import com.seed.enums.AppHttpCodeEnum;
import com.seed.exception.SystemException;
import com.seed.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2024/1/10 20:44
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
//提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }
}
