package com.seed.ruoyi.core.domain;


import com.seed.domain.entity.system.SysMoitor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.format.DateTimeFormatter;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMoitorVo {
    /**
     *
     */
    private String sysTotalMemory;

    /**
     *
     */
    private String sysFreeMemory;

    /**
     *
     */
    private String cpuCore;

    /**
     *
     */
    private String jvmHeapMemoryMax;

    /**
     *
     */
    private String jvmHeapMemoryUsed;

    /**
     *
     */
    private String sysCpuUsed;

    /**
     *
     */
    private String jvmCpuUsed;

    /**
     *
     */
    private String diskTotalSize;

    /**
     *
     */
    private String diskFreeSize;

    /**
     *
     */
    private String database;

    /**
     *
     */
    private String redis;

    /**
     *
     */
    private String createTime;

    public SysMoitorVo(SysMoitor sysMoitor) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        /* 37 */
        String createTime = sysMoitor.getCreateTime().format(formatter);
        /* 38 */
        this.sysTotalMemory = formatByteToGB(sysMoitor.getSysTotalMemory());
        /* 39 */
        this.sysFreeMemory = formatByteToGB(sysMoitor.getSysFreeMemory());
        /* 40 */
        this.cpuCore = String.valueOf(sysMoitor.getCpuCore());
        /* 41 */
        this.jvmHeapMemoryMax = formatByteToGB(sysMoitor.getJvmHeapMemoryMax());
        /* 42 */
        this.jvmHeapMemoryUsed = formatByteToGB(sysMoitor.getJvmHeapMemoryUsed());
        /* 43 */
        this.sysCpuUsed = formatCpuPercentage(sysMoitor.getSysCpuUsed());
        /* 44 */
        this.jvmCpuUsed = formatCpuPercentage(sysMoitor.getJvmCpuUsed());
        /* 45 */
        this.diskTotalSize = formatByteToGB(sysMoitor.getDiskTotalSize());
        /* 46 */
        this.diskFreeSize = formatByteToGB(sysMoitor.getDiskFreeSize());
        /* 47 */
        this.database = sysMoitor.getDatabase();
        /* 48 */
        this.redis = sysMoitor.getRedis();
        /* 49 */
        this.createTime = createTime;
    }
    private String formatByteToGB(long size) {
        /* 55 */
        return String.format("%.2f", new Object[]{Double.valueOf(size / 1024.0D / 1024.0D / 1024.0D)});
        /*    */
    }
//
//    /*    */
//    /*    */
    private String formatCpuPercentage(double size) {
        /* 59 */
        return String.format("%.2f", new Object[]{Double.valueOf(size * 100.0D)});
        /*    */
    }

    public static SysMoitorVo.SysMoitorVoBuilder builder() {
        return new SysMoitorVo.SysMoitorVoBuilder();
    }

    public static class SysMoitorVoBuilder {
        private String sysTotalMemory;
        private String sysFreeMemory;
        private String cpuCore;
        private String jvmHeapMemoryMax;
        private String jvmHeapMemoryUsed;
        private String sysCpuUsed;
        private String jvmCpuUsed;
        private String diskTotalSize;
        private String diskFreeSize;
        private String database;
        private String redis;
        private String createTime;

        public SysMoitorVo.SysMoitorVoBuilder sysTotalMemory(String sysTotalMemory) {
            this.sysTotalMemory = sysTotalMemory;
            return this;
        }


        public SysMoitorVo.SysMoitorVoBuilder sysFreeMemory(String sysFreeMemory) {
            this.sysFreeMemory = sysFreeMemory;
            return this;
        }

        public SysMoitorVo.SysMoitorVoBuilder cpuCore(String cpuCore) {
            this.cpuCore = cpuCore;
            return this;
        }

        public SysMoitorVo.SysMoitorVoBuilder jvmHeapMemoryMax(String jvmHeapMemoryMax) {
            this.jvmHeapMemoryMax = jvmHeapMemoryMax;
            return this;
        }

        public SysMoitorVo.SysMoitorVoBuilder jvmHeapMemoryUsed(String jvmHeapMemoryUsed) {
            this.jvmHeapMemoryUsed = jvmHeapMemoryUsed;
            return this;
        }

        public SysMoitorVo.SysMoitorVoBuilder sysCpuUsed(String sysCpuUsed) {
            this.sysCpuUsed = sysCpuUsed;
            return this;
        }

        public SysMoitorVo.SysMoitorVoBuilder jvmCpuUsed(String jvmCpuUsed) {
            this.jvmCpuUsed = jvmCpuUsed;
            return this;
        }

        public SysMoitorVo.SysMoitorVoBuilder diskTotalSize(String diskTotalSize) {
            this.diskTotalSize = diskTotalSize;
            return this;
        }

        public SysMoitorVo.SysMoitorVoBuilder diskFreeSize(String diskFreeSize) {
            this.diskFreeSize = diskFreeSize;
            return this;
        }

        public SysMoitorVo.SysMoitorVoBuilder database(String database) {
            this.database = database;
            return this;
        }

        public SysMoitorVo.SysMoitorVoBuilder redis(String redis) {
            this.redis = redis;
            return this;
        }

        public SysMoitorVo.SysMoitorVoBuilder createTime(String createTime) {
            this.createTime = createTime;
            return this;
        }

        public SysMoitorVo build() {
            return new SysMoitorVo(this.sysTotalMemory, this.sysFreeMemory, this.cpuCore, this.jvmHeapMemoryMax, this.jvmHeapMemoryUsed, this.sysCpuUsed, this.jvmCpuUsed, this.diskTotalSize, this.diskFreeSize, this.database, this.redis, this.createTime);
        }
    }
}
