package com.seed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.domain.ResponseResult;
import com.seed.domain.entity.User;
import com.seed.domain.vo.UserInfoVo;
import com.seed.mapper.UserMapper;
import com.seed.service.UserService;
import com.seed.utils.BeanCopyUtils;
import com.seed.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author 77286
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-12-16 21:51:29
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
implements UserService{
    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //格局id查询用户信息
        User user = getById(userId);
        //封装UserInfoVo返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }
}
