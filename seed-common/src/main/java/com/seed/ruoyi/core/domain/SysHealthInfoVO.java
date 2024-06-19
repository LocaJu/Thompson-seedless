///*    */
//package com.seed.ruoyi.core.domain;
//
//import java.time.format.DateTimeFormatter;
//
///*    */
///*    */ public class SysHealthInfoVO {
//    /*    */   private String sysTotalMemory;
//    /*    */   private String sysFreeMemory;
//    /*    */   private String cpuCore;
//    /*    */   private String jvmHeapMemoryMax;
//    /*    */   private String jvmHeapMemoryUsed;
//    /*    */   private String sysCpuUsed;
//    /*    */   private String jvmCpuUsed;
//    /*    */   private String diskTotalSize;
//    /*    */   private String diskFreeSize;
//    /*    */   private String database;
//    /*    */   private String redis;
//    /*    */   private String createTime;
//
//    /*    */
//    /* 17 */
//    public void setSysTotalMemory(String sysTotalMemory) {
//        this.sysTotalMemory = sysTotalMemory;
//    }
//
//    public void setSysFreeMemory(String sysFreeMemory) {
//        this.sysFreeMemory = sysFreeMemory;
//    }
//
//    public void setCpuCore(String cpuCore) {
//        this.cpuCore = cpuCore;
//    }
//
//    public void setJvmHeapMemoryMax(String jvmHeapMemoryMax) {
//        this.jvmHeapMemoryMax = jvmHeapMemoryMax;
//    }
//
//    public void setJvmHeapMemoryUsed(String jvmHeapMemoryUsed) {
//        this.jvmHeapMemoryUsed = jvmHeapMemoryUsed;
//    }
//
//    public void setSysCpuUsed(String sysCpuUsed) {
//        this.sysCpuUsed = sysCpuUsed;
//    }
//
//    public void setJvmCpuUsed(String jvmCpuUsed) {
//        this.jvmCpuUsed = jvmCpuUsed;
//    }
//
//    public void setDiskTotalSize(String diskTotalSize) {
//        this.diskTotalSize = diskTotalSize;
//    }
//
//    public void setDiskFreeSize(String diskFreeSize) {
//        this.diskFreeSize = diskFreeSize;
//    }
//
//    public void setDatabase(String database) {
//        this.database = database;
//    }
//
//    public void setRedis(String redis) {
//        this.redis = redis;
//    }
//
//    public void setCreateTime(String createTime) {
//        this.createTime = createTime;
//    }
//
//    public boolean equals(Object o) {
//        if (o == this) return true;
//        if (!(o instanceof SysHealthInfoVO)) return false;
//        SysHealthInfoVO other = (SysHealthInfoVO) o;
//        if (!other.canEqual(this)) return false;
//        Object this$sysTotalMemory = getSysTotalMemory(), other$sysTotalMemory = other.getSysTotalMemory();
//        if ((this$sysTotalMemory == null) ? (other$sysTotalMemory != null) : !this$sysTotalMemory.equals(other$sysTotalMemory))
//            return false;
//        Object this$sysFreeMemory = getSysFreeMemory(), other$sysFreeMemory = other.getSysFreeMemory();
//        if ((this$sysFreeMemory == null) ? (other$sysFreeMemory != null) : !this$sysFreeMemory.equals(other$sysFreeMemory))
//            return false;
//        Object this$cpuCore = getCpuCore(), other$cpuCore = other.getCpuCore();
//        if ((this$cpuCore == null) ? (other$cpuCore != null) : !this$cpuCore.equals(other$cpuCore)) return false;
//        Object this$jvmHeapMemoryMax = getJvmHeapMemoryMax(), other$jvmHeapMemoryMax = other.getJvmHeapMemoryMax();
//        if ((this$jvmHeapMemoryMax == null) ? (other$jvmHeapMemoryMax != null) : !this$jvmHeapMemoryMax.equals(other$jvmHeapMemoryMax))
//            return false;
//        Object this$jvmHeapMemoryUsed = getJvmHeapMemoryUsed(), other$jvmHeapMemoryUsed = other.getJvmHeapMemoryUsed();
//        if ((this$jvmHeapMemoryUsed == null) ? (other$jvmHeapMemoryUsed != null) : !this$jvmHeapMemoryUsed.equals(other$jvmHeapMemoryUsed))
//            return false;
//        Object this$sysCpuUsed = getSysCpuUsed(), other$sysCpuUsed = other.getSysCpuUsed();
//        if ((this$sysCpuUsed == null) ? (other$sysCpuUsed != null) : !this$sysCpuUsed.equals(other$sysCpuUsed))
//            return false;
//        Object this$jvmCpuUsed = getJvmCpuUsed(), other$jvmCpuUsed = other.getJvmCpuUsed();
//        if ((this$jvmCpuUsed == null) ? (other$jvmCpuUsed != null) : !this$jvmCpuUsed.equals(other$jvmCpuUsed))
//            return false;
//        Object this$diskTotalSize = getDiskTotalSize(), other$diskTotalSize = other.getDiskTotalSize();
//        if ((this$diskTotalSize == null) ? (other$diskTotalSize != null) : !this$diskTotalSize.equals(other$diskTotalSize))
//            return false;
//        Object this$diskFreeSize = getDiskFreeSize(), other$diskFreeSize = other.getDiskFreeSize();
//        if ((this$diskFreeSize == null) ? (other$diskFreeSize != null) : !this$diskFreeSize.equals(other$diskFreeSize))
//            return false;
//        Object this$database = getDatabase(), other$database = other.getDatabase();
//        if ((this$database == null) ? (other$database != null) : !this$database.equals(other$database)) return false;
//        Object this$redis = getRedis(), other$redis = other.getRedis();
//        if ((this$redis == null) ? (other$redis != null) : !this$redis.equals(other$redis)) return false;
//        Object this$createTime = getCreateTime(), other$createTime = other.getCreateTime();
//        return !((this$createTime == null) ? (other$createTime != null) : !this$createTime.equals(other$createTime));
//    }
//
//    protected boolean canEqual(Object other) {
//        return other instanceof SysHealthInfoVO;
//    }
//
//
//
//    public String toString() {
//        return "SysHealthInfoVO(sysTotalMemory=" + getSysTotalMemory() + ", sysFreeMemory=" + getSysFreeMemory() + ", cpuCore=" + getCpuCore() + ", jvmHeapMemoryMax=" + getJvmHeapMemoryMax() + ", jvmHeapMemoryUsed=" + getJvmHeapMemoryUsed() + ", sysCpuUsed=" + getSysCpuUsed() + ", jvmCpuUsed=" + getJvmCpuUsed() + ", diskTotalSize=" + getDiskTotalSize() + ", diskFreeSize=" + getDiskFreeSize() + ", database=" + getDatabase() + ", redis=" + getRedis() + ", createTime=" + getCreateTime() + ")";
//    }
//
//    /* 18 */
//    public static SysHealthInfoVOBuilder builder() {
//        return new SysHealthInfoVOBuilder();
//    }
//
//    public static class SysHealthInfoVOBuilder {
//        private String sysTotalMemory;
//        private String sysFreeMemory;
//        private String cpuCore;
//        private String jvmHeapMemoryMax;
//        private String jvmHeapMemoryUsed;
//        private String sysCpuUsed;
//
//        public SysHealthInfoVOBuilder sysTotalMemory(String sysTotalMemory) {
//            this.sysTotalMemory = sysTotalMemory;
//            return this;
//        }
//
//        private String jvmCpuUsed;
//        private String diskTotalSize;
//        private String diskFreeSize;
//        private String database;
//        private String redis;
//        private String createTime;
//
//        public SysHealthInfoVOBuilder sysFreeMemory(String sysFreeMemory) {
//            this.sysFreeMemory = sysFreeMemory;
//            return this;
//        }
//
//        public SysHealthInfoVOBuilder cpuCore(String cpuCore) {
//            this.cpuCore = cpuCore;
//            return this;
//        }
//
//        public SysHealthInfoVOBuilder jvmHeapMemoryMax(String jvmHeapMemoryMax) {
//            this.jvmHeapMemoryMax = jvmHeapMemoryMax;
//            return this;
//        }
//
//        public SysHealthInfoVOBuilder jvmHeapMemoryUsed(String jvmHeapMemoryUsed) {
//            this.jvmHeapMemoryUsed = jvmHeapMemoryUsed;
//            return this;
//        }
//
//        public SysHealthInfoVOBuilder sysCpuUsed(String sysCpuUsed) {
//            this.sysCpuUsed = sysCpuUsed;
//            return this;
//        }
//
//        public SysHealthInfoVOBuilder jvmCpuUsed(String jvmCpuUsed) {
//            this.jvmCpuUsed = jvmCpuUsed;
//            return this;
//        }
//
//        public SysHealthInfoVOBuilder diskTotalSize(String diskTotalSize) {
//            this.diskTotalSize = diskTotalSize;
//            return this;
//        }
//
//        public SysHealthInfoVOBuilder diskFreeSize(String diskFreeSize) {
//            this.diskFreeSize = diskFreeSize;
//            return this;
//        }
//
//        public SysHealthInfoVOBuilder database(String database) {
//            this.database = database;
//            return this;
//        }
//
//        public SysHealthInfoVOBuilder redis(String redis) {
//            this.redis = redis;
//            return this;
//        }
//
//        public SysHealthInfoVOBuilder createTime(String createTime) {
//            this.createTime = createTime;
//            return this;
//        }
//
//        public SysHealthInfoVO build() {
//            return new SysHealthInfoVO(this.sysTotalMemory, this.sysFreeMemory, this.cpuCore, this.jvmHeapMemoryMax, this.jvmHeapMemoryUsed, this.sysCpuUsed, this.jvmCpuUsed, this.diskTotalSize, this.diskFreeSize, this.database, this.redis, this.createTime);
//        }
//
//        public String toString() {
//            return "SysHealthInfoVO.SysHealthInfoVOBuilder(sysTotalMemory=" + this.sysTotalMemory + ", sysFreeMemory=" + this.sysFreeMemory + ", cpuCore=" + this.cpuCore + ", jvmHeapMemoryMax=" + this.jvmHeapMemoryMax + ", jvmHeapMemoryUsed=" + this.jvmHeapMemoryUsed + ", sysCpuUsed=" + this.sysCpuUsed + ", jvmCpuUsed=" + this.jvmCpuUsed + ", diskTotalSize=" + this.diskTotalSize + ", diskFreeSize=" + this.diskFreeSize + ", database=" + this.database + ", redis=" + this.redis + ", createTime=" + this.createTime + ")";
//        }
//    }
//
//    public SysHealthInfoVO(String sysTotalMemory, String sysFreeMemory, String cpuCore, String jvmHeapMemoryMax, String jvmHeapMemoryUsed, String sysCpuUsed, String jvmCpuUsed, String diskTotalSize, String diskFreeSize, String database, String redis, String createTime) {
//        /* 19 */
//        this.sysTotalMemory = sysTotalMemory;
//        this.sysFreeMemory = sysFreeMemory;
//        this.cpuCore = cpuCore;
//        this.jvmHeapMemoryMax = jvmHeapMemoryMax;
//        this.jvmHeapMemoryUsed = jvmHeapMemoryUsed;
//        this.sysCpuUsed = sysCpuUsed;
//        this.jvmCpuUsed = jvmCpuUsed;
//        this.diskTotalSize = diskTotalSize;
//        this.diskFreeSize = diskFreeSize;
//        this.database = database;
//        this.redis = redis;
//        this.createTime = createTime;
//        /*    */
//    }
//
//    /*    */
//    public SysHealthInfoVO() {
//    }
//
//    /* 22 */
//    public String getSysTotalMemory() {
//        return this.sysTotalMemory;
//    }
//
//    /* 23 */
//    public String getSysFreeMemory() {
//        return this.sysFreeMemory;
//    }
//
//    /* 24 */
//    public String getCpuCore() {
//        return this.cpuCore;
//    }
//
//    /* 25 */
//    public String getJvmHeapMemoryMax() {
//        return this.jvmHeapMemoryMax;
//    }
//
//    /* 26 */
//    public String getJvmHeapMemoryUsed() {
//        return this.jvmHeapMemoryUsed;
//    }
//
//    /* 27 */
//    public String getSysCpuUsed() {
//        return this.sysCpuUsed;
//    }
//
//    /* 28 */
//    public String getJvmCpuUsed() {
//        return this.jvmCpuUsed;
//    }
//
//    /* 29 */
//    public String getDiskTotalSize() {
//        return this.diskTotalSize;
//    }
//
//    /* 30 */
//    public String getDiskFreeSize() {
//        return this.diskFreeSize;
//    }
//
//    /* 31 */
//    public String getDatabase() {
//        return this.database;
//    }
//
//    /* 32 */
//    public String getRedis() {
//        return this.redis;
//    }
//
//    public String getCreateTime() {
//        /* 33 */
//        return this.createTime;
//        /*    */
//    }
//
//    /*    */
//    public SysHealthInfoVO(SysHealthInfo sysHealthInfo) {
//        /* 36 */
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        /* 37 */
//        String createTime = sysHealthInfo.getCreateTime().format(formatter);
//        /* 38 */
//        this.sysTotalMemory = formatByteToGB(sysHealthInfo.getSysTotalMemory());
//        /* 39 */
//        this.sysFreeMemory = formatByteToGB(sysHealthInfo.getSysFreeMemory());
//        /* 40 */
//        this.cpuCore = String.valueOf(sysHealthInfo.getCpuCore());
//        /* 41 */
//        this.jvmHeapMemoryMax = formatByteToGB(sysHealthInfo.getJvmHeapMemoryMax());
//        /* 42 */
//        this.jvmHeapMemoryUsed = formatByteToGB(sysHealthInfo.getJvmHeapMemoryUsed());
//        /* 43 */
//        this.sysCpuUsed = formatCpuPercentage(sysHealthInfo.getSysCpuUsed());
//        /* 44 */
//        this.jvmCpuUsed = formatCpuPercentage(sysHealthInfo.getJvmCpuUsed());
//        /* 45 */
//        this.diskTotalSize = formatByteToGB(sysHealthInfo.getDiskTotalSize());
//        /* 46 */
//        this.diskFreeSize = formatByteToGB(sysHealthInfo.getDiskFreeSize());
//        /* 47 */
//        this.database = sysHealthInfo.getDatabase();
//        /* 48 */
//        this.redis = sysHealthInfo.getRedis();
//        /* 49 */
//        this.createTime = createTime;
//        /*    */
//    }
//
//    /*    */
//    /*    */
//    /*    */
//    /*    */
//    private String formatByteToGB(long size) {
//        /* 55 */
//        return String.format("%.2f", new Object[]{Double.valueOf(size / 1024.0D / 1024.0D / 1024.0D)});
//        /*    */
//    }
//
//    /*    */
//    /*    */
//    private String formatCpuPercentage(double size) {
//        /* 59 */
//        return String.format("%.2f", new Object[]{Double.valueOf(size * 100.0D)});
//        /*    */
//    }
//    /*    */
//}
//
//
///* Location:              D:\apache-maven-3.6.3\repository\com\qince\agent-admin-dependency-system\1.0.3\agent-admin-dependency-system-1.0.3.jar!\com\qince\system\domain\SysHealthInfoVO.class
// * Java compiler version: 17 (61.0)
// * JD-Core Version:       1.1.3
// */