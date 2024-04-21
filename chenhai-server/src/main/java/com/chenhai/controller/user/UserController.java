package com.chenhai.controller.user;

import com.chenhai.constant.JwtClaimsConstant;
import com.chenhai.context.BaseContext;
import com.chenhai.dto.UserAccountOrPhoneLoginDTO;
import com.chenhai.dto.UserRegisterDTO;
import com.chenhai.dto.user.UserInfoDTO;
import com.chenhai.dto.user.UserPageQueryDTO;
import com.chenhai.entity.Tag;
import com.chenhai.entity.User;
import com.chenhai.exception.BaseException;
import com.chenhai.properties.JwtProperties;
import com.chenhai.result.PageResult;
import com.chenhai.result.Result;
import com.chenhai.service.UserService;
import com.chenhai.utils.AliSmsUtil;
import com.chenhai.utils.JwtUtil;
import com.chenhai.utils.JwtUtils;
import com.chenhai.vo.UserLoginOrRegisterVO;
import com.chenhai.vo.user.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Api(tags = "C端用户相关接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;;

    @Autowired
    private AliSmsUtil aliSmsUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtUtils jwtUtils;
    //token过期时间
    private static final Integer TOKEN_EXPIRE_DAYS = 5;
    //token续期时间
    private static final Integer TOKEN_RENEWAL_DAYS = 2;

    // @PostMapping("/login")
    // @ApiOperation("微信登录")
    // public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
    //     log.info("微信用户登录: {}", userLoginDTO.getCode());
    //
    //     // 微信登录
    //     User user = userService.wxLogin(userLoginDTO);
    //
    //     // 为微信用户生成jwt令牌
    //     Map<String, Object> claims = new HashMap<>();
    //     claims.put(JwtClaimsConstant.USER_ID, user.getId());
    //     String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
    //
    //     UserLoginVO userLoginVO = UserLoginVO.builder().
    //             id(user.getId())
    //             .openid(user.getOpenid())
    //             .token(token)
    //             .build();
    //     return Result.success(userLoginVO);
    // }


    /**
     * 账号注册
     * @param userRegisterDTO
     * @return
     */
    @PostMapping("/register")
    public Result<UserLoginOrRegisterVO> register(@RequestBody UserRegisterDTO userRegisterDTO) throws Exception {
        log.info("用户注册: {}", userRegisterDTO);

        // 用户注册
        Long userId = userService.register(userRegisterDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, userId);
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        // token = jwtUtil.login(userId);

        UserLoginOrRegisterVO userAccountLoginVO = UserLoginOrRegisterVO.builder()
                .id(Long.valueOf(userId))
                .token(token)
                .build();

        return Result.success(userAccountLoginVO);
    }

    /**
     * 获取验证码
     * @param mobile
     * @return
     */
    @PostMapping("/verifiCode")
    public Result getVerifiCode(String mobile) throws Exception {
        log.info("获取验证码: {}", mobile);
        return userService.sendMsg(mobile);
    }

    /**
     * 账号登录
     * @param userAccountOrPhoneLoginDTO
     * @return UserAccountLoginOrRegisterVO
     */
    @PostMapping("/login")
    @ApiOperation("账号登录")
    public Result<UserLoginOrRegisterVO> login(@RequestBody UserAccountOrPhoneLoginDTO userAccountOrPhoneLoginDTO) {
        log.info("员工登录: {}", userAccountOrPhoneLoginDTO);

        // 账号登录
        User user = userService.accountOrPhoneLogin(userAccountOrPhoneLoginDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);
        // token = jwtUtil.login(user.getId());



        UserLoginOrRegisterVO userAccountLoginVO = UserLoginOrRegisterVO.builder()
                .id(user.getId())
                .token(token)
                .build();

        return Result.success(userAccountLoginVO);
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/userInfo")
    @ApiOperation("获取用户信息")
    public Result<User> getInfo() {
        log.info("获取用户信息");
        Long userId = BaseContext.getCurrentId();
        User userInfo = userService.getUserInfo(userId);

        return Result.success(userInfo);
    }

    /**
     * 修改用户信息
     * @return
     */
    @PutMapping("/userInfo")
    @ApiOperation("修改用户信息")
    public Result<UserInfoVO> resetInfo(@RequestBody UserInfoDTO userInfoDTO) {
        log.info("修改用户信息");
        UserInfoVO userInfoVO = userService.resetUserInfo(userInfoDTO);

        return Result.success(userInfoVO);
    }

    /**
     * 用户签到
     * @return
     */
    @GetMapping("/sign")
    @ApiOperation("用户签到")
    public Result sign() {
        log.info("用户签到");
        userService.sign();
        return Result.success();
    }

    /**
     * 分页获取用户列表
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("用户分页查询")
    public Result<PageResult> pageQuery(UserPageQueryDTO userPageQueryDTO) {
        log.info("分页获取用户数据");
        PageResult pageResult = userService.pageQuery(userPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据标签搜索用户
     * @param tagNameList
     * @return
     */
    @GetMapping("/search/tags")
    @ApiOperation("根据标签搜索用户")
    public Result<List<User>> searchUsersByTags(@RequestParam(required = false) List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BaseException("参数出错");
        }
        List<User> userList = userService.searchUsersByTags(tagNameList);
        return Result.success(userList);
    }

    /**
     * 获取所有的标签
     * @return
     */
    @GetMapping("/tags")
    @ApiOperation("获取所有的tag")
    public Result<List<Tag>> tagList() {
        List<Tag> list = userService.tagList();
        return Result.success(list);
    }

    /**
     * 匹配
     * @param num
     * @return
     */
    @GetMapping("/match")
    public Result<List<User>> matchUsers(long num) {
        if (num <= 0 || num > 20) {
            throw new BaseException("");
        }
        User user = userService.getLoginUser();
        return Result.success(userService.matchUsers(num, user));
    }
}
