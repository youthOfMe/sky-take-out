package com.chenhai.controller.admin;

import com.chenhai.constant.JwtClaimsConstant;
import com.chenhai.context.BaseContext;
import com.chenhai.dto.EmployeeDTO;
import com.chenhai.dto.EmployeeLoginDTO;
import com.chenhai.dto.EmployeePageQueryDTO;
import com.chenhai.entity.Employee;
import com.chenhai.properties.JwtProperties;
import com.chenhai.result.PageResult;
import com.chenhai.result.Result;
import com.chenhai.service.EmployeeService;
import com.chenhai.utils.JwtUtil;
import com.chenhai.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/userInfo")
    @ApiOperation("获取用户信息")
    public Result<Employee> getInfo() {
        log.info("获取用户信息");
        Long userId = BaseContext.getCurrentId();
        Employee userInfo = employeeService.getUserInfo(userId);

        return Result.success(userInfo);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("员工退出登录")
    public Result<String> logout() {
        return Result.success();
    }

    // 新增员工
    @PostMapping
    @ApiOperation("新增员工")
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工: {}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    // 分页查询员工
    @GetMapping("/page")
    @ApiOperation("员工分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("员工分页查询, 参数为: {}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    // 启用禁用员工账号
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用员工账号")
    public Result startOrStop(@PathVariable Integer status, Long id){
        log.info("启用禁用员工账号, {}, {}", status, id);
        employeeService.startOrStop(status, id);
        return Result.success();
    }

    // 根据ID进行查询员工信息
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询员工信息")
    public Result<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    // 编辑员工信息
    @PutMapping
    @ApiOperation("编辑员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("编辑员工信息: {}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }
}
