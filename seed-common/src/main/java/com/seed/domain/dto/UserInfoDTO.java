package com.seed.domain.dto;

import lombok.Data;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/30 22:36
 */
@Data
public class UserInfoDTO {
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
    /**
     * 性别
     */
    private String sex;
    /**
     * 邮箱
     */
    private String email;
}
