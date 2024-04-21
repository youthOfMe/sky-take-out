package com.sky.service;

import com.sky.dto.UserAccountOrPhoneLoginDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.dto.UserRegisterDTO;
import com.sky.dto.user.UserInfoDTO;
import com.sky.dto.user.UserPageQueryDTO;
import com.sky.entity.User;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.user.UserInfoVO;

import java.util.List;

public interface UserService {

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    User wxLogin(UserLoginDTO userLoginDTO);

    /**
     * 账号登录
     * @param userAccountOrPhoneLoginDTO
     * @return
     */
    User accountOrPhoneLogin(UserAccountOrPhoneLoginDTO userAccountOrPhoneLoginDTO);

    /**
     * 用户注册
     * @return
     */
    Long register(UserRegisterDTO userRegisterDTO) throws Exception;

    /**
     * 发送验证码
     * @param mobile
     */
    Result sendMsg(String mobile) throws Exception;

    /**
     * 获取当前登录用户信息
     * @return
     */
    User getLoginUser();

    /**
     * 获取用户信息
     * @return
     */
    User getUserInfo(Long userId);

    /**
     * 修改用户信息
     * @param userInfoDTO
     */
    UserInfoVO resetUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 用户签到
     */
    void sign();

    /**
     * 分页获取用户列表
     * @return
     */
    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);

    /**
     * 根据标签搜索用户
     *
     * @param tagNameList
     * @return
     */
    List<User> searchUsersByTags(List<String> tagNameList);

    /**
     * 匹配算法
     * @param num
     * @param user
     * @return
     */
    List<User> matchUsers(long num, User user);
}
