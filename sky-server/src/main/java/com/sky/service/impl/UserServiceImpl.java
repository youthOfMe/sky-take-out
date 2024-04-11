package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserAccountLoginDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.dto.UserRegisterDTO;
import com.sky.entity.User;
import com.sky.exception.AccountExisted;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.LoginFailedException;
import com.sky.exception.PasswordNotFoundException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    // 微信服务接口地址
    public  static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());

        // 判断openid是否为空 如果为空就表示登录失败 抛出业务异常
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        // 判断当前用户是否为新用户
        User user = userMapper.getByOpenid(openid);

        // 如果是心用户自动完成注册
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        return user;
    }

    private String getOpenid(String code) {
        // 调用微信服务接口服务 获得当前用户端
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, map);

        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");

        return openid;
    }

    /**
     * 账号登录
     * @param userAccountLoginDTO
     * @return
     */
    public User accountLogin(UserAccountLoginDTO userAccountLoginDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", userAccountLoginDTO.getAccount());
        queryWrapper.eq("password", userAccountLoginDTO.getPassword());

        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            throw new AccountNotFoundException("未找到该账号");
        }

        return user;
    }

    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    public Integer register(UserRegisterDTO userRegisterDTO) {

        if (userRegisterDTO.getAccount() == null) {
            throw new AccountNotFoundException("账号不许为空");
        }
        if (userRegisterDTO.getPassword() == null) {
            throw new PasswordNotFoundException("密码不许为空");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", userRegisterDTO.getAccount());
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            throw new AccountExisted("账号已存在");
        }

        user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        Integer userId = userMapper.insert(user);

        return userId;
    }
}
