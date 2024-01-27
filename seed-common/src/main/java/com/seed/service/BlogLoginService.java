package com.seed.service;

import com.seed.domain.ResponseResult;
import com.seed.domain.entity.system.SysUser;

public interface BlogLoginService {
    ResponseResult login(SysUser user);

    ResponseResult logout();
}
