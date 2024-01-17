package com.seed.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

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
     * 状态0:正常,1禁用
     */
    private String status;

}
