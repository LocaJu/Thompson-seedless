package com.seed.domain.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分类表
 * @TableName tb_category
 */
@TableName(value ="tb_category")
@Data
public class Category implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 分类名
     */
    private String name;

    /**
     * 父分类id，如果没有父分类为-1
     */
    private Long pid;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态0:正常,1禁用
     */
    private String status;

    /**
     * 
     */
    private Long create_by;

    /**
     * 
     */
    private Date create_time;

    /**
     * 
     */
    private Long update_by;

    /**
     * 
     */
    private Date update_time;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer del_flag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}