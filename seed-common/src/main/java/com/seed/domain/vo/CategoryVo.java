package com.seed.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/15 19:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryVo {
    private Long id;
    private String name;
}
