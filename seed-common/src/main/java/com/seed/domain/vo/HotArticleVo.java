package com.seed.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/10 18:56
 */
@Data
public class HotArticleVo implements Serializable {
    private Long id;

    //标题
    private String title;

    //访问量
    private Long viewCount;
}
