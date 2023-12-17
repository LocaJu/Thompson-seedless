package com.seed.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 文章标签关联表
 * @TableName tb_article_tag
 */
@TableName(value ="tb_article_tag")
@Data
public class ArticleTag implements Serializable {
    /**
     * 文章id
     */

    private Long articleId;

    /**
     * 标签id
     */

    private Long tagId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}