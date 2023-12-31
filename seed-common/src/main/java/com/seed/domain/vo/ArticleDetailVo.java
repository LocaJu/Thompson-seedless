package com.seed.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/16 20:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo implements Serializable {

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
     * 所属分类id
     */
    private Long categoryId;

    /**
     * 所属分类名称
     */
    private String categoryName;

    /**
     * 缩略图
     */
    private String thumbnail;


    /**
     * 访问量
     */
    private Long viewCount;

    /**
     * 是否允许评论 1是，0否
     */

    private String isComment;

    /**
     *
     */
    private Long createBy;

    /**
     *
     */
    private Date createTime;


    private static final long serialVersionUID = 1L;
}
