package com.seed.domain.dto;

import lombok.Data;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/31 21:32
 */
@Data
public class UserRegiterDTO {
    private String userName;

    private String password;

    private String nickName;

    private String email;
}
