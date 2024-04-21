package com.chenhai.service;

import com.chenhai.dto.UserAccountOrPhoneLoginDTO;
import com.chenhai.dto.UserLoginDTO;
import com.chenhai.dto.UserRegisterDTO;
import com.chenhai.dto.user.UserInfoDTO;
import com.chenhai.dto.user.UserPageQueryDTO;
import com.chenhai.entity.Tag;
import com.chenhai.entity.User;
import com.chenhai.result.PageResult;
import com.chenhai.result.Result;
import com.chenhai.vo.user.UserInfoVO;

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

    /**
     * 获取所有的标签
     * @return
     */
    List<Tag> tagList();
}
