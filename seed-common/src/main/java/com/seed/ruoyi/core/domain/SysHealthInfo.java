///*    */
//package com.seed.ruoyi.core.domain;
//
//import lombok.Data;
//import lombok.Getter;
//
//import java.time.LocalDateTime;
//@Data
//public class SysHealthInfo {
//    private long sysTotalMemory;
//    private long sysFreeMemory;
//    private long cpuCore;
//    private long jvmHeapMemoryMax;
//    private long jvmHeapMemoryUsed;
//    private double sysCpuUsed;
//    private double jvmCpuUsed;
//    private long diskTotalSize;
//    private long diskFreeSize;
//    private String database;
//    private String redis;
//    private LocalDateTime createTime;
//
//
//
//    /* 15 */
//    public static SysHealthInfoBuilder builder() {
//        return new SysHealthInfoBuilder();
//    }
//
//    public static class SysHealthInfoBuilder {
//        private long sysTotalMemory;
//        private long sysFreeMemory;
//        private long cpuCore;
//        private long jvmHeapMemoryMax;
//        private long jvmHeapMemoryUsed;
//        private double sysCpuUsed;
//
//        public SysHealthInfoBuilder sysTotalMemory(long sysTotalMemory) {
//            this.sysTotalMemory = sysTotalMemory;
//            return this;
//        }
//
//        private double jvmCpuUsed;
//        private long diskTotalSize;
//        private long diskFreeSize;
//        private String database;
//        private String redis;
//        private LocalDateTime createTime;
//
//        public SysHealthInfoBuilder sysFreeMemory(long sysFreeMemory) {
//            this.sysFreeMemory = sysFreeMemory;
//            return this;
//        }
//
//        public SysHealthInfoBuilder cpuCore(long cpuCore) {
//            this.cpuCore = cpuCore;
//            return this;
//        }
//
//        public SysHealthInfoBuilder jvmHeapMemoryMax(long jvmHeapMemoryMax) {
//            this.jvmHeapMemoryMax = jvmHeapMemoryMax;
//            return this;
//        }
//
//        public SysHealthInfoBuilder jvmHeapMemoryUsed(long jvmHeapMemoryUsed) {
//            this.jvmHeapMemoryUsed = jvmHeapMemoryUsed;
//            return this;
//        }
//
//        public SysHealthInfoBuilder sysCpuUsed(double sysCpuUsed) {
//            this.sysCpuUsed = sysCpuUsed;
//            return this;
//        }
//
//        public SysHealthInfoBuilder jvmCpuUsed(double jvmCpuUsed) {
//            this.jvmCpuUsed = jvmCpuUsed;
//            return this;
//        }
//
//        public SysHealthInfoBuilder diskTotalSize(long diskTotalSize) {
//            this.diskTotalSize = diskTotalSize;
//            return this;
//        }
//
//        public SysHealthInfoBuilder diskFreeSize(long diskFreeSize) {
//            this.diskFreeSize = diskFreeSize;
//            return this;
//        }
//
//        public SysHealthInfoBuilder database(String database) {
//            this.database = database;
//            return this;
//        }
//
//        public SysHealthInfoBuilder redis(String redis) {
//            this.redis = redis;
//            return this;
//        }
//
//        public SysHealthInfoBuilder createTime(LocalDateTime createTime) {
//            this.createTime = createTime;
//            return this;
//        }
//
//        public SysHealthInfo build() {
//            return new SysHealthInfo(this.sysTotalMemory, this.sysFreeMemory, this.cpuCore, this.jvmHeapMemoryMax, this.jvmHeapMemoryUsed, this.sysCpuUsed, this.jvmCpuUsed, this.diskTotalSize, this.diskFreeSize, this.database, this.redis, this.createTime);
//        }
//
//        public String toString() {
//            return "SysHealthInfo.SysHealthInfoBuilder(sysTotalMemory=" + this.sysTotalMemory + ", sysFreeMemory=" + this.sysFreeMemory + ", cpuCore=" + this.cpuCore + ", jvmHeapMemoryMax=" + this.jvmHeapMemoryMax + ", jvmHeapMemoryUsed=" + this.jvmHeapMemoryUsed + ", sysCpuUsed=" + this.sysCpuUsed + ", jvmCpuUsed=" + this.jvmCpuUsed + ", diskTotalSize=" + this.diskTotalSize + ", diskFreeSize=" + this.diskFreeSize + ", database=" + this.database + ", redis=" + this.redis + ", createTime=" + this.createTime + ")";
//        }
//    }
//
//}
//
//
///* Location:              D:\apache-maven-3.6.3\repository\com\qince\agent-admin-dependency-system\1.0.3\agent-admin-dependency-system-1.0.3.jar!\com\qince\system\domain\SysHealthInfo.class
// * Java compiler version: 17 (61.0)
// * JD-Core Version:       1.1.3
// */