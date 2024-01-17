package com.seed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seed.domain.entity.SysDictType;
import org.apache.ibatis.annotations.Mapper;

/**
* @author qince
* @description 针对表【sys_dict_type(字典类型表)】的数据库操作Mapper
* @createDate 2024-01-17 15:01:45
* @Entity com.seed.domain.entity.SysDictType
*/
@Mapper
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {

    int deleteByPrimaryKey(Long id);

    int insert(SysDictType record);

    int insertSelective(SysDictType record);

    SysDictType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDictType record);

    int updateByPrimaryKey(SysDictType record);

}
