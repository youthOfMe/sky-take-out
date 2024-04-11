package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserAccountLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserAccountLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/accountLogin")
    @ApiOperation("账号登录")
    public Result<UserAccountLoginVO> login(@RequestBody UserAccountLoginDTO userAccountLoginDTO) {
        log.info("员工登录: {}", userAccountLoginDTO);

        // 账号登录
        User user = userService.accountLogin(userAccountLoginDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserAccountLoginVO userAccountLoginVO = UserAccountLoginVO.builder()
                .id(user.getId())
                .token(token)
                .build();

        return Result.success(userAccountLoginVO);
    }

}
