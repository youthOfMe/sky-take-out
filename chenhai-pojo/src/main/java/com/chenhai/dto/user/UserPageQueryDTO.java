package com.chenhai.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserPageQueryDTO implements Serializable {

    // 用户名称
    private String name;

    //页码
    private int page;

    //每页记录数
    private int pageSize;
}
