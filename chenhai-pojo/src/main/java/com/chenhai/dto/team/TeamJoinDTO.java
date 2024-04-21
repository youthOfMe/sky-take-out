package com.chenhai.dto.team;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeamJoinDTO implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * id
     */
    private Long teamId;

    /**
     * 密码
     */
    private String password;
}
