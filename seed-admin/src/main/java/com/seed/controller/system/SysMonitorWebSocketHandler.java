package com.seed.controller.system;


import com.alibaba.fastjson2.JSONArray;
import com.seed.domain.entity.system.SysMoitor;
import com.seed.ruoyi.core.domain.SysMoitorVo;
import com.seed.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class SysMonitorWebSocketHandler {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;



    @Autowired
    private RedisCache redisCache;
    private final String MONITOR_KEY = "system:monitor";
    private static LocalDateTime finalTime = LocalDateTime.now();

    public SysMonitorWebSocketHandler() {
    }

    @MessageMapping({"/monitor"})
    @SendTo({"/topic/sysInfoInit"})
    public JSONArray greeting() {
        LocalDateTime nowTime = LocalDateTime.now();
        long now = System.currentTimeMillis();
        long thirtyMinBefore = now - 1800000L;
        Set<SysMoitor> dataSetByRange = this.redisCache.getDataSetByRange("system:monitor", thirtyMinBefore, now);
        List<SysMoitorVo> sysMoitorVos = new ArrayList();
        dataSetByRange.stream().forEach((item) -> {
            sysMoitorVos.add(new SysMoitorVo(item));
        });
        finalTime = nowTime;

        return new JSONArray(sysMoitorVos);
    }

    @Scheduled(
            fixedRate = 5000L
    )
    public void sendSysInfo() {
        SysMoitor sysHealthInfo = (SysMoitor) this.redisCache.getHighSocreItemFormZset("system:monitor");
        if (sysHealthInfo != null) {
            SysMoitorVo sysHealthInfoVO = new SysMoitorVo(sysHealthInfo);
            if (finalTime.isBefore(sysHealthInfo.getCreateTime())) {
                finalTime = sysHealthInfo.getCreateTime();
                this.messagingTemplate.convertAndSend("/topic/sysInfo", sysHealthInfoVO);
            }

        }
    }
}
