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
 * @date 2023/12/15 20:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *id
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
     * @description: 分类名称
     * @version 1.0
     **/
    private String categoryName;

    /**
     * 缩略图
     */
    private String thumbnail;

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
    private Date create_time;


}
