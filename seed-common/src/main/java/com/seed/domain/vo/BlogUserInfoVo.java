package com.seed.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/17 15:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUserInfoVo {
    private String token;
    private UserInfoVo userInfo;
}
