package com.seed.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/17 15:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    private String sex;
    private String email;

}
