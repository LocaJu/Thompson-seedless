package com.seed.service;

import com.seed.domain.ResponseResult;
import com.seed.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
