//package com.seed.service.impl;
//
//import com.seed.domain.entity.system.SysUser;
//import com.seed.domain.model.LoginUser;
//import com.seed.mapper.UserMapper;
//import com.seed.mapper.system.SysUserMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Objects;
//
///**
// * @author 77286
// * @version 1.0
// * @description: TODO
// * @date 2023/12/17 15:30
// */
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private SysUserMapper sysUserMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//            //根据用户名查询用户信息
////            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
////            queryWrapper.eq(User::getUserName, username);
////            User user = userMapper.selectOne(queryWrapper);
//            SysUser sysUser = sysUserMapper.selectUserByUserName(username);
//            //判断是否查到用户 如果没查到抛出异常
//            if (Objects.isNull(sysUser)) {
//                throw new RuntimeException("用户不存在");
//            }
//            //返回用户信息
//            // TODO 查询权限信息封装
//
//            return new LoginUser(sysUser);
//        }
//
//    }
//
