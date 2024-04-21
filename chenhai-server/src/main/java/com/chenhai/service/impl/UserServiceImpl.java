package com.chenhai.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.chenhai.constant.MessageConstant;
import com.chenhai.context.BaseContext;
import com.chenhai.dto.UserAccountOrPhoneLoginDTO;
import com.chenhai.dto.UserLoginDTO;
import com.chenhai.dto.UserRegisterDTO;
import com.chenhai.dto.user.UserInfoDTO;
import com.chenhai.dto.user.UserPageQueryDTO;
import com.chenhai.entity.Tag;
import com.chenhai.entity.User;
import com.chenhai.exception.*;
import com.chenhai.mapper.UserMapper;
import com.chenhai.mapper.tag.TagMapper;
import com.chenhai.properties.WeChatProperties;
import com.chenhai.result.PageResult;
import com.chenhai.result.Result;
import com.chenhai.service.UserService;
import com.chenhai.utils.AlgorithmUtils;
import com.chenhai.utils.AliSmsUtil;
import com.chenhai.utils.HttpClientUtil;
import com.chenhai.vo.user.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    // 微信服务接口地址
    public  static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TagMapper tagMapper;

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
     * 获取当前登录用户信息
     * @return
     */
    public User getLoginUser() {
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.selectById(userId);

        return user;
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
        if (userInfoDTO.getType() == 1) {
            if (StringUtils.isBlank(userInfoDTO.getName()) || StringUtils.isEmpty(userInfoDTO.getName())) throw new BaseException("用户昵称不可为空");
        }
        if (userInfoDTO.getType() == 2) {
            if (StringUtils.isBlank(userInfoDTO.getAvatar()) || StringUtils.isEmpty(userInfoDTO.getAvatar())) throw new BaseException("用户头像不可为空");
        }
        Long userId = BaseContext.getCurrentId();
        User user = new User();

        BeanUtils.copyProperties(userInfoDTO, user);
        user.setId(userId);

        userMapper.updateById(user);

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setName(userInfoDTO.getName());
        userInfoVO.setAvatar(userInfoDTO.getAvatar());

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

    /**
     * 根据标签搜索用户（内存过滤）
     *
     * @param tagNameList 用户要拥有的标签
     * @return
     */
    @Override
    public List<User> searchUsersByTags(List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BaseException("参数出错");
        }
        // 1. 先查询所有用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> userList = userMapper.selectList(queryWrapper);
        Gson gson = new Gson();
        // 2. 在内存中判断是否包含要求的标签
        return userList.stream().filter(user -> {
            String tagsStr = user.getTags();
            Set<String> tempTagNameSet = gson.fromJson(tagsStr, new TypeToken<Set<String>>() {
            }.getType());
            tempTagNameSet = Optional.ofNullable(tempTagNameSet).orElse(new HashSet<>());
            for (String tagName : tagNameList) {
                if (!tempTagNameSet.contains(tagName)) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }

    /**
     * 匹配算法
     * @param num
     * @param user
     * @return
     */
    @Override
    public List<User> matchUsers(long num, User loginUser) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "tags");
        queryWrapper.isNotNull("tags");
        List<User> userList = this.list(queryWrapper);
        String tags = loginUser.getTags();
        Gson gson = new Gson();
        List<String> tagList = gson.fromJson(tags, new TypeToken<List<String>>() {
        }.getType());
        // 用户列表的下标 => 相似度
        List<Pair<User, Long>> list = new ArrayList<>();
        // 依次计算所有用户和当前用户的相似度
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            String userTags = user.getTags();
            // 无标签或者为当前用户自己
            if (org.apache.commons.lang3.StringUtils.isBlank(userTags) || user.getId() == loginUser.getId()) {
                continue;
            }
            List<String> userTagList = gson.fromJson(userTags, new TypeToken<List<String>>() {
            }.getType());
            // 计算分数
            long distance = AlgorithmUtils.minDistance(tagList, userTagList);
            list.add(new Pair<>(user, distance));
        }
        // 按编辑距离由小到大排序
        List<Pair<User, Long>> topUserPairList = list.stream()
                .sorted((a, b) -> (int) (a.getValue() - b.getValue()))
                .limit(num)
                .collect(Collectors.toList());
        // 原本顺序的 userId 列表
        List<Long> userIdList = topUserPairList.stream().map(pair -> pair.getKey().getId()).collect(Collectors.toList());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.in("id", userIdList);
        // 1, 3, 2
        // User1、User2、User3
        // 1 => User1, 2 => User2, 3 => User3
        Map<Long, List<User>> userIdUserListMap = this.list(userQueryWrapper)
                .stream()
                .collect(Collectors.groupingBy(User::getId));
        List<User> finalUserList = new ArrayList<>();
        for (Long userId : userIdList) {
            finalUserList.add(userIdUserListMap.get(userId).get(0));
        }
        return finalUserList;
    }

    /**
     * 获取所有的标签
     * @return
     */
    public List<Tag> tagList() {
        List<Tag> list = tagMapper.selectList(null);
        return list;
    }
}
