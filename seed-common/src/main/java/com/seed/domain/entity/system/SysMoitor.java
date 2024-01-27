package com.seed.domain.entity.system;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName sys_moitor
 */
@TableName(value ="sys_moitor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMoitor implements Serializable {
    /**
     * 
     */
    private long sysTotalMemory;

    /**
     * 
     */
    private long sysFreeMemory;

    /**
     * 
     */
    private long cpuCore;

    /**
     * 
     */
    private long jvmHeapMemoryMax;

    /**
     * 
     */
    private long jvmHeapMemoryUsed;

    /**
     * 
     */
    private double sysCpuUsed;

    /**
     * 
     */
    private double jvmCpuUsed;

    /**
     * 
     */
    private long diskTotalSize;

    /**
     * 
     */
    private long diskFreeSize;

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
    private LocalDateTime createTime;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;




    public static SysMoitorBuilder builder() {
        return new SysMoitorBuilder();
    }
    public static class SysMoitorBuilder {
        private long sysTotalMemory;
        private long sysFreeMemory;
        private long cpuCore;
        private long jvmHeapMemoryMax;
        private long jvmHeapMemoryUsed;
        private double sysCpuUsed;
        private double jvmCpuUsed;
        private long diskTotalSize;
        private long diskFreeSize;
        private String database;
        private String redis;
        private LocalDateTime createTime;

        public SysMoitorBuilder sysTotalMemory(long sysTotalMemory) {
            this.sysTotalMemory = sysTotalMemory;
            return this;
        }


        public SysMoitorBuilder sysFreeMemory(long sysFreeMemory) {
            this.sysFreeMemory = sysFreeMemory;
            return this;
        }

        public SysMoitorBuilder cpuCore(long cpuCore) {
            this.cpuCore = cpuCore;
            return this;
        }

        public SysMoitorBuilder jvmHeapMemoryMax(long jvmHeapMemoryMax) {
            this.jvmHeapMemoryMax = jvmHeapMemoryMax;
            return this;
        }

        public SysMoitorBuilder jvmHeapMemoryUsed(long jvmHeapMemoryUsed) {
            this.jvmHeapMemoryUsed = jvmHeapMemoryUsed;
            return this;
        }

        public SysMoitorBuilder sysCpuUsed(double sysCpuUsed) {
            this.sysCpuUsed = sysCpuUsed;
            return this;
        }

        public SysMoitorBuilder jvmCpuUsed(double jvmCpuUsed) {
            this.jvmCpuUsed = jvmCpuUsed;
            return this;
        }

        public SysMoitorBuilder diskTotalSize(long diskTotalSize) {
            this.diskTotalSize = diskTotalSize;
            return this;
        }

        public SysMoitorBuilder diskFreeSize(long diskFreeSize) {
            this.diskFreeSize = diskFreeSize;
            return this;
        }

        public SysMoitorBuilder database(String database) {
            this.database = database;
            return this;
        }

        public SysMoitorBuilder redis(String redis) {
            this.redis = redis;
            return this;
        }

        public SysMoitorBuilder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }
        public SysMoitor build() {
            return new SysMoitor(this.sysTotalMemory, this.sysFreeMemory, this.cpuCore, this.jvmHeapMemoryMax, this.jvmHeapMemoryUsed, this.sysCpuUsed, this.jvmCpuUsed, this.diskTotalSize, this.diskFreeSize, this.database, this.redis, this.createTime);
        }

    }
}