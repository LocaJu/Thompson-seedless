package com.seed.domain.entity.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HardwareMonitor {
    private String id;

    private String hardware;

    private String useage;

    private Date createTime;
}
