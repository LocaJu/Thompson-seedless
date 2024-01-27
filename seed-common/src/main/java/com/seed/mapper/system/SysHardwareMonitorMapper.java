package com.seed.mapper.system;

import com.seed.domain.entity.system.HardwareMonitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;


public interface SysHardwareMonitorMapper {
    List<HardwareMonitor> selectHardwareMonitor(@Param("start") String start, @Param("end") String end, @Param("hardware") String hardware);

    List<BigDecimal> selectHardwareUseage(@Param("start") String start, @Param("end") String end, @Param("hardware") String hardware);

    void batchInsert(List<HardwareMonitor> list);

    void deleteBefore();
}
