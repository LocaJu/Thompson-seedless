package com.seed.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 文章表
 * @TableName tb_article
 */
@TableName(value ="tb_article")
@Data
public class Article implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 所属分类id
     */
    private Long category_id;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 是否置顶（0否，1是）
     */
    private String is_top;

    /**
     * 状态（0已发布，1草稿）
     */
    private String status;

    /**
     * 访问量
     */
    private Long view_count;

    /**
     * 是否允许评论 1是，0否
     */

    private String is_comment;

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