package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.UserAccountOrPhoneLoginDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.dto.UserRegisterDTO;
import com.sky.dto.user.UserInfoDTO;
import com.sky.dto.user.UserPageQueryDTO;
import com.sky.entity.User;
import com.sky.exception.*;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.AliSmsUtil;
import com.sky.utils.HttpClientUtil;
import com.sky.vo.user.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AliSmsUtil aliSmsUtil;

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
     * @param userAccountOrPhoneLoginDTO
     * @return
     */
    public User accountOrPhoneLogin(UserAccountOrPhoneLoginDTO userAccountOrPhoneLoginDTO) {
        if (userAccountOrPhoneLoginDTO.getType() == 1 && userAccountOrPhoneLoginDTO.getAccount() == null) {
            throw new AccountNotFoundException("账号不许为空");
        }
        if (userAccountOrPhoneLoginDTO.getPassword() == null) {
            throw new PasswordNotFoundException("密码不许为空");
        }
        // 验证是否填写了手机号
        if (userAccountOrPhoneLoginDTO.getType() == 2 && userAccountOrPhoneLoginDTO.getPhone() == null) {
            throw new PhoneNotFoundException("手机号不许为空");
        }
        // 验证验证码是否正确
        if (userAccountOrPhoneLoginDTO.getType() == 2) {
            String key = "CHECK_CODE" + userAccountOrPhoneLoginDTO.getPhone();
            String value = (String) redisTemplate.opsForValue().get(key);
            if (!StringUtils.equals(value, userAccountOrPhoneLoginDTO.getCode())) {
                throw new VerifiCodeNotFoundException("验证码输入不正确");
            }
        }

        User user = null;
        // 验证账号是否存在
        if (userAccountOrPhoneLoginDTO.getType() == 2) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone", userAccountOrPhoneLoginDTO.getPhone());
            user = userMapper.selectOne(queryWrapper);
        } else if (userAccountOrPhoneLoginDTO.getType() == 1) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account", userAccountOrPhoneLoginDTO.getAccount());
            queryWrapper.eq("password", userAccountOrPhoneLoginDTO.getPassword());
            user = userMapper.selectOne(queryWrapper);
        }
        if (user == null) {
            throw new AccountExisted("账号不存在");
        }

        return user;
    }

    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    public Long register(UserRegisterDTO userRegisterDTO) {

        if (userRegisterDTO.getType() == 1 && userRegisterDTO.getAccount() == null) {
            throw new AccountNotFoundException("账号不许为空");
        }
        if (userRegisterDTO.getType() == 1 && userRegisterDTO.getPassword() == null) {
            throw new PasswordNotFoundException("密码不许为空");
        }

        // 验证是否填写了手机号
        if (userRegisterDTO.getType() == 2 && userRegisterDTO.getPhone() == null) {
            throw new PhoneNotFoundException("手机号不许为空");
        }
        // 验证是否提交了验证码
        if (userRegisterDTO.getType() == 2 && userRegisterDTO.getCode() == null) {
            throw new VerifiCodeNotFoundException("请填写验证码");
        }
        // 验证验证码是否正确
        if (userRegisterDTO.getType() == 2) {
            String key = "CHECK_CODE" + userRegisterDTO.getPhone();
            String value = (String) redisTemplate.opsForValue().get(key);
            if (!StringUtils.equals(value, userRegisterDTO.getCode())) {
                throw new VerifiCodeNotFoundException("验证码输入不正确");
            }
        }
        User user = null;
        // 验证账号是否存在
        if (userRegisterDTO.getType() == 2) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone", userRegisterDTO.getPhone());
            user = userMapper.selectOne(queryWrapper);
        } else if (userRegisterDTO.getType() == 1) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account", userRegisterDTO.getAccount());
            user = userMapper.selectOne(queryWrapper);
        }
        if (user != null) {
            throw new AccountExisted("账号已存在");
        }

        user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        Integer userId = userMapper.insert(user);

        return Long.valueOf(userId);
    }

    /**
     * 发送验证码
     * @param mobile
     */
    public Result sendMsg(String mobile) throws Exception {
        // 1. 随机生成6位数字
        String code = RandomStringUtils.randomNumeric(6);
        String key = "CHECK_CODE" + mobile;
        String value = (String) redisTemplate.opsForValue().get(key);
        if (value != null) {
            return Result.error("获取验证码太频繁了！！！");
        }
        // 2. 调用template对象, 发送手机对象
        aliSmsUtil.sendSms(mobile, code);
        // 3. 讲验证码存入到redis
        redisTemplate.opsForValue().set("CHECK_CODE_" + mobile, code, Duration.ofMinutes(5));
        return Result.success();
    }

    /**
     * 获取用户信息
     * @return
     */
    public User getUserInfo(Long userId) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        User user = userMapper.selectOne(queryWrapper);

        // 用户数据脱敏
        user.setUpdateTime(null);
        user.setCreateTime(null);
        user.setPassword(null);

        return user;
    }

    /**
     * 修改用户信息
     * @param userInfoDTO
     */
    public UserInfoVO resetUserInfo(UserInfoDTO userInfoDTO) {
        Long userId = BaseContext.getCurrentId();
        User user = new User();

        BeanUtils.copyProperties(userInfoDTO, user);
        user.setId(userId);

        userMapper.updateById(user);

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setName(userInfoDTO.getName());

        return userInfoVO;

    }

    /**
     * 用户签到
     */
    public void sign() {
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.selectById(userId);
        if (user.getIsSign() == 1) {
            throw new BaseException("您已经进行签到过了!");
        }
        user.setSignIcon(user.getSignIcon() + 5);
        user.setIsSign(1);
        user.setSignDay(user.getSignDay() + 1);

        userMapper.updateById(user);
    }

    /**
     * 分页获取用户列表
     * @return
     */
    public PageResult pageQuery(UserPageQueryDTO userPageQueryDTO) {
        PageHelper.startPage(userPageQueryDTO.getPage(), userPageQueryDTO.getPageSize());
        Page<User> page = userMapper.pageQuery(userPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

}
