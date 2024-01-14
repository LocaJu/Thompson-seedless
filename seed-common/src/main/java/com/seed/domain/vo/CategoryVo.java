package com.seed.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/15 19:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    /**
     * 描述
     */
    private String description;}
