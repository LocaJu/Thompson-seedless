package com.seed.service.system.monitor;


import com.seed.domain.entity.system.SysMoitor;
import com.seed.mapper.system.SysMoitorMapper;

import com.seed.utils.RedisCache;
import com.sun.management.OperatingSystemMXBean;
import io.micrometer.core.instrument.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.data.redis.RedisHealthIndicator;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SystemMoitorService {
    private static final Logger log = LoggerFactory.getLogger(SystemMoitorService.class);
    private final String MONITOR_KEY = "system:monitor";

    @Autowired
    private MeterRegistry meterRegistry;
    @Autowired
    private SysMoitorMapper sysMoitorMapper;
    @Autowired
    private RedisCache redisCache;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    public SystemMoitorService() {

    }

    @Scheduled(
            fixedRate = 120000L
    )
    private void systemMonitor() {

        SysMoitor sysMoitor = SysMoitor.builder().createTime(LocalDateTime.now()).sysTotalMemory(this.getSystemTotalMemory()).sysFreeMemory(this.getSystemFreeMemory()).cpuCore((long)this.getCpuCore()).jvmHeapMemoryMax(this.getJVMMaxMemory()).jvmHeapMemoryUsed(this.getJVMHeapMemoryUsed()).sysCpuUsed(this.getSysCpuUsed()).jvmCpuUsed(this.getJvmCpuUsed()).diskTotalSize(this.getDiskTotalSize()).diskFreeSize(this.getDiskFreeSize()).database(this.getDatabaseHealthInfo()).redis(this.getRedisHealthInfo()).build();
        this.redisCache.addDataToSortedSet(MONITOR_KEY, sysMoitor, (double)System.currentTimeMillis());
    }

    @Scheduled(
            fixedRate = 3600000L
    )
    private void persistenceMonitor() {
        long thirtyMinBefore = System.currentTimeMillis() - 3600000L;
        Set<SysMoitor> dataSetByRange = this.redisCache.getDataSetByRange("system:monitor", 0L, thirtyMinBefore);
        if (!CollectionUtils.isEmpty(dataSetByRange)) {
            this.sysMoitorMapper.insertBatch(dataSetByRange);
            this.redisCache.removeDataSetByRange("system:monitor", 0L, thirtyMinBefore);
        }

    }

    private long getSystemTotalMemory() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        return osBean.getTotalPhysicalMemorySize();
    }

    private long getSystemFreeMemory() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean)ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        return osBean.getFreePhysicalMemorySize();
    }

    private int getCpuCore() {
        return Runtime.getRuntime().availableProcessors();
    }

    private long getJVMMaxMemory() {
        List<Tag> tags = new ArrayList();
        Tag tag = new ImmutableTag("area", "heap");
        tags.add(tag);
        Collection<Meter> meters = this.meterRegistry.find("jvm.memory.max").tags(tags).meters();
        double sumHeapMax = 0.0;
        Iterator var6 = meters.iterator();

        while(var6.hasNext()) {
            Meter meter = (Meter)var6.next();
            Iterable<Measurement> measure = meter.measure();
            Iterator var9 = measure.iterator();

            while(var9.hasNext()) {
                Measurement measurement = (Measurement)var9.next();
                double genValue = measurement.getValue();
                if (genValue >= 0.0) {
                    sumHeapMax += genValue;
                }
            }
        }

        return (long)sumHeapMax;
    }

    private long getJVMHeapMemoryUsed() {
        List<Tag> tags = new ArrayList();
        Collection<Meter> meters = this.meterRegistry.find("jvm.memory.used").tags(tags).meters();
        double sumHeapUsed = 0.0;
        Iterator var5 = meters.iterator();

        while(var5.hasNext()) {
            Meter meter = (Meter)var5.next();
            Iterable<Measurement> measure = meter.measure();
            Iterator var8 = measure.iterator();

            while(var8.hasNext()) {
                Measurement measurement = (Measurement)var8.next();
                double genValue = measurement.getValue();
                if (genValue >= 0.0) {
                    sumHeapUsed += genValue;
                }
            }
        }

        return (long)sumHeapUsed;
    }

    private double getSysCpuUsed() {
        Meter processCpuUsedMeter = this.meterRegistry.find("system.cpu.usage").meter();
        Iterable<Measurement> processCpuUsedMeasure = processCpuUsedMeter.measure();
        Iterator<Measurement> processCpuUsedMeasureIterator = processCpuUsedMeasure.iterator();
        Measurement processCpuUsedMeasureNext = (Measurement)processCpuUsedMeasureIterator.next();
        return processCpuUsedMeasureNext.getValue();
    }

    private double getJvmCpuUsed() {
        Meter processCpuUsedMeter = this.meterRegistry.find("process.cpu.usage").meter();
        Iterable<Measurement> processCpuUsedMeasure = processCpuUsedMeter.measure();
        Iterator<Measurement> processCpuUsedMeasureIterator = processCpuUsedMeasure.iterator();
        Measurement processCpuUsedMeasureNext = (Measurement)processCpuUsedMeasureIterator.next();
        return processCpuUsedMeasureNext.getValue();
    }

    private long getDiskTotalSize() {
        Meter diskTotalMeter = this.meterRegistry.find("disk.total").meter();
        Iterable<Measurement> diskTotalMeterMeasure = diskTotalMeter.measure();
        Iterator<Measurement> diskTotalMeterMeasureIterator = diskTotalMeterMeasure.iterator();
        Measurement diskTotalMeasureNext = (Measurement)diskTotalMeterMeasureIterator.next();
        return (long)diskTotalMeasureNext.getValue();
    }

    private long getDiskFreeSize() {
        Meter diskFreeMeter = this.meterRegistry.find("disk.free").meter();
        Iterable<Measurement> diskFreeMeterMeasure = diskFreeMeter.measure();
        Iterator<Measurement> diskFreeMeterMeasureIterator = diskFreeMeterMeasure.iterator();
        Measurement diskFreeMeasureNext = (Measurement)diskFreeMeterMeasureIterator.next();
        return (long)diskFreeMeasureNext.getValue();
    }

    @Autowired
    DataSource dataSource;
    private String getDatabaseHealthInfo() {
        DataSourceHealthIndicator dataSourceHealthIndicator = new DataSourceHealthIndicator(dataSource);
        HealthComponent datasourceHealth = dataSourceHealthIndicator.health();
        return datasourceHealth.getStatus().toString();
    }

    private String getRedisHealthInfo() {
        RedisHealthIndicator redisHealthIndicator = new RedisHealthIndicator(redisConnectionFactory);

        HealthComponent redisHealth = redisHealthIndicator.health();
        return redisHealth.getStatus().toString();
    }
}
