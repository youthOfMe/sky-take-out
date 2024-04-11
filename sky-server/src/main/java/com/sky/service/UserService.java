package com.sky.service;

import com.sky.dto.UserAccountLoginDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.dto.UserRegisterDTO;
import com.sky.entity.User;

public interface UserService {

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    User wxLogin(UserLoginDTO userLoginDTO);

    /**
     * 账号登录
     * @param userAccountLoginDTO
     * @return
     */
    User accountLogin(UserAccountLoginDTO userAccountLoginDTO);

    /**
     * 用户注册
     * @return
     */
    Integer register(UserRegisterDTO userRegisterDTO) throws Exception;
}
