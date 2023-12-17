package com.seed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seed.domain.ResponseResult;
import com.seed.domain.entity.Link;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();
}
