package com.seed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seed.domain.entity.SysDictData;
import org.apache.ibatis.annotations.Mapper;


/**
* @author qince
* @description 针对表【sys_dict_data(字典数据表)】的数据库操作Mapper
* @createDate 2024-01-17 14:57:24
* @Entity com.seed.domain.entity.SysDictData
*/
@Mapper
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    int deleteByPrimaryKey(Long id);

    int insert(SysDictData record);

    int insertSelective(SysDictData record);

    SysDictData selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDictData record);

    int updateByPrimaryKey(SysDictData record);

}
