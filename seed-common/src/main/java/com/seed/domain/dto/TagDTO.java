package com.seed.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2024/1/14 17:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    private Long id;
    private String name;
    private String remark;
}
