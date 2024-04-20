package com.sky.dto.user;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoDTO implements Serializable {

    // 用户昵称
    private String name;

    // 用户头像
}
