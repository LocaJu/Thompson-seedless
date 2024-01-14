package com.seed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seed.domain.entity.Role;

import java.util.List;

/**
* @author 77286
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2023-12-16 21:21:49
* @Entity com.seed.domain.entity.Role
*/

public interface RoleMapper extends BaseMapper<Role> {


    List<String> selectRoleKeyByUserId(Long id);
}
