package com.seed.service;

import com.seed.domain.ResponseResult;
import com.seed.ruoyi.core.domain.AjaxResult;

public interface LoginService {
    AjaxResult login(String username, String password, String code, String uuid);

    public ResponseResult logout();
}
