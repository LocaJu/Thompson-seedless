package com.seed.service;

import com.seed.domain.ResponseResult;
import com.seed.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

    public ResponseResult logout();
}
