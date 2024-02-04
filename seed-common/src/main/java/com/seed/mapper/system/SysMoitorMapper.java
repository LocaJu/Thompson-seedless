package com.seed.mapper.system;

import com.seed.domain.entity.system.SysMoitor;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
* @author qince
* @description 针对表【sys_moitor】的数据库操作Mapper
* @createDate 2024-01-25 11:26:25
* @Entity com.seed.domain.entity.system.SysMoitor
*/
public interface SysMoitorMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysMoitor record);

    void insertBatch(@Param("sysMoitorSet") Set<SysMoitor> sysMoitorSet);

    int insertSelective(SysMoitor record);

    SysMoitor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysMoitor record);

    int updateByPrimaryKey(SysMoitor record);

}
