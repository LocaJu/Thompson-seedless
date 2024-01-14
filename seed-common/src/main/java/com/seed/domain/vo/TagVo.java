package com.seed.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2024/1/14 17:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo implements Serializable {

    private Long id;

    /**
     * 标签名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;
}
