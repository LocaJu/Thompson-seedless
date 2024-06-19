//package com.seed.controller;
//
//import com.seed.domain.entity.Menu;
//
//import com.seed.domain.entity.system.SysMenu;
//import com.seed.domain.entity.system.SysUser;
//import com.seed.domain.model.LoginUser;
//import com.seed.ruoyi.core.domain.AjaxResult;
//import com.seed.service.system.ISysMenuService;
//import com.seed.service.system.ISysRoleService;
//import com.seed.service.system.web.service.SysPermissionService;
//import com.seed.utils.SecurityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.Set;
//
///**
// * @author 77286
// * @version 1.0
// * @description: TODO
// * @date 2024/1/10 21:46
// */
//@RestController
//@RequestMapping
//public class UserController {
//
//    @Autowired
//    private ISysMenuService menuService;
//
//    @Autowired
//    private ISysRoleService roleService;
//
//    @Autowired
//    private SysPermissionService permissionService;
//
////    @GetMapping("/getInfo")
////    public AjaxResult getInfo(){
////        //获取当前登录用户
////        LoginUser loginUser = SecurityUtils.getLoginUser();
////        //获取用户权限
////        List<String> permissions =menuService.selectPermByUserId(loginUser.getUser().getId());
////        //获取用户角色
////        List<String> roles =roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
////        User user = loginUser.getUser();
//////        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
////        //封装返回用户信息
////        AjaxResult ajax = AjaxResult.success();
////        ajax.put("user", user);
////        ajax.put("roles", roles);
////        ajax.put("permissions", permissions);
////        return ajax;
////
////    }
//
//    /**
//     * 获取用户信息
//     *
//     * @return 用户信息
//     */
//    @GetMapping("getInfo")
//    public AjaxResult getInfo()
//    {
//        SysUser user = SecurityUtils.getLoginUser().getUser();
//        // 角色集合
//        Set<String> roles = permissionService.getRolePermission(user);
//        // 权限集合
//        Set<String> permissions = permissionService.getMenuPermission(user);
//        AjaxResult ajax = AjaxResult.success();
//        ajax.put("user", user);
//        ajax.put("roles", roles);
//        ajax.put("permissions", permissions);
//        return ajax;
//    }
//
//
////    @GetMapping("getRouters")
////    public AjaxResult getRouters(){
////        //获取当前登录用户
////        Long userId = SecurityUtils.getUserId();
////        //查询menu 结果是tree的形式
////        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
////        //封装数据返回
////        return AjaxResult.success(menus);
//////        return ResponseResult.okResult(new RoutersVo(menus));
////    }
//    /**
//     * 获取路由信息
//     *
//     * @return 路由信息
//     */
//    @GetMapping("getRouters")
//    public AjaxResult getRouters()
//    {
//        Long userId = SecurityUtils.getUserId();
//        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
//        return AjaxResult.success(menuService.buildMenus(menus));
//    }
//
//}
