package com.seed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.domain.entity.User;
import com.seed.mapper.UserMapper;
import com.seed.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author 77286
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-12-16 21:51:29
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
implements UserService{

}
