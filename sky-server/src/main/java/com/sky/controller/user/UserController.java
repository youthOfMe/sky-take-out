package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserAccountOrPhoneLoginDTO;
import com.sky.dto.UserRegisterDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.AliSmsUtil;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginOrRegisterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        Integer userId = userService.register(userRegisterDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, userId);
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);


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
     * @param userAccountLoginDTO
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

        UserLoginOrRegisterVO userAccountLoginVO = UserLoginOrRegisterVO.builder()
                .id(user.getId())
                .token(token)
                .build();

        return Result.success(userAccountLoginVO);
    }



}
