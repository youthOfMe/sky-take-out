package com.chenhai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountOrPhoneLoginDTO {
    private Integer type;

    private String account;

    private String password;

    private String phone;

    private String code;
}
