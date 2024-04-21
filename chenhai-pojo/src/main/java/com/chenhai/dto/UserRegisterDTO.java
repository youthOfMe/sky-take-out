package com.chenhai.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    /**
     * 1 -> 账号登录 2 -> 验证码登录
     */
    private Integer type;

    private String account;

    private String password;

    private String phone;

    private String code;
}
