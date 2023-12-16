package com.seed.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/15 20:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private List rows;
    private Long total;
}
