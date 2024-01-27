package com.seed.controller;

import com.seed.annotation.SystemLog;
import com.seed.domain.ResponseResult;
import com.seed.domain.dto.UserInfoDTO;
import com.seed.domain.dto.UserRegiterDTO;
import com.seed.service.system.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/18 20:44
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ISysUserService userService;
    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    /*
     * @description: 修改用户信息
     * @author: 77286
     * @date: 2023/12/31 22:58
     * @param: MultipartFile file
     * @return: com.seed.domain.ResponseResult
     * @version 1.0
     **/
    @PutMapping("/userInfo")
    @SystemLog(businessName = "修改下用户信息")
    public ResponseResult updateUserInfo(@RequestBody UserInfoDTO userInfoDTO){
        return userService.updateUserInfo(userInfoDTO);
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody UserRegiterDTO userRegiterDTO){
        return userService.register(userRegiterDTO);
    }
}
