package com.chenhai.service;

import com.chenhai.dto.EmployeeDTO;
import com.chenhai.dto.EmployeeLoginDTO;
import com.chenhai.dto.EmployeePageQueryDTO;
import com.chenhai.entity.Employee;
import com.chenhai.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    // 新增员工
    void save(EmployeeDTO employeeDTO);

    // 分页查询
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    // 启用禁用员工账号
    void startOrStop(Integer status, Long id);

    // 根据ID查询员工信息
    Employee getById(Long id);

    // 编辑员工信息
    void update(EmployeeDTO employeeDTO);

    // 获取员工信息
    Employee getUserInfo(Long userId);
}
