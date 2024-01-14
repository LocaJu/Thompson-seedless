package com.seed.controller;

import com.seed.domain.LoginUser;
import com.seed.domain.ResponseResult;
import com.seed.domain.entity.Menu;
import com.seed.domain.entity.User;
import com.seed.domain.vo.AdminUserInfoVo;
import com.seed.domain.vo.RoutersVo;
import com.seed.domain.vo.UserInfoVo;
import com.seed.service.MenuService;
import com.seed.service.RoleService;
import com.seed.service.UserRoleService;
import com.seed.utils.BeanCopyUtils;
import com.seed.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2024/1/10 21:46
 */
@RestController
@RequestMapping
public class UserController {

    @Autowired
    MenuService menuService;

    @Autowired
    RoleService roleService;

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //获取用户权限
        List<String> perms =menuService.selectPermByUserId(loginUser.getUser().getId());
        //获取用户角色
        List<String> roles =roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装返回用户信息
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roles,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }


    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        //获取当前登录用户
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }
}
