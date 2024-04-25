package com.chenhai.controller.admin.user;

import com.chenhai.dto.user.UserPageQueryDTO;
import com.chenhai.result.PageResult;
import com.chenhai.result.Result;
import com.chenhai.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController("adminUserController")
@RequestMapping("/admin/user")
@Slf4j
@Api(tags = "用户管理相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    // 分页查询员工
    @GetMapping("/page")
    @ApiOperation("用户分页查询")
    public Result<PageResult> page(UserPageQueryDTO userPageQueryDTO) {
        log.info("用户分页查询, 参数为: {}", userPageQueryDTO);
        PageResult pageResult = userService.pageQuery(userPageQueryDTO);
        return Result.success(pageResult);
    }
}
