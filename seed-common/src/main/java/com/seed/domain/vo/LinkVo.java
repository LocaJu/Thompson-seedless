package com.seed.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/16 21:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo implements Serializable {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String logo;

    /**
     *
     */
    private String description;

    /**
     * 网站地址
     */
    private String address;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
