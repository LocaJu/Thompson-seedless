//package com.seed.service.system.monitor;
//
//import io.micrometer.core.instrument.MeterRegistry;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//
//@Configuration
//public class MyMetricsConfiguration {
//    @Autowired
//    private MeterRegistry meterRegistry;
//
//    @PostConstruct
//    public void registerCustomMetrics() {
//        // 注册自定义指标
//        meterRegistry.gauge("system.cpu.usage",3);
//    }
//}
