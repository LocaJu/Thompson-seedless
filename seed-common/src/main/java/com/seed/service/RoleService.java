package com.seed.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.seed.domain.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<String> selectRoleKeyByUserId(Long id);
}
