package com.seed.domain.vo;


import com.seed.domain.entity.system.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2024/1/14 15:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {
    private List<SysMenu> menus;
}
