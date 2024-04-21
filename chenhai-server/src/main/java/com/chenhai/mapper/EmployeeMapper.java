package com.chenhai.mapper;

import com.github.pagehelper.Page;
import com.chenhai.annotation.AutoFill;
import com.chenhai.dto.EmployeePageQueryDTO;
import com.chenhai.entity.Employee;
import com.chenhai.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    // 插入员工数据
    @Insert("insert into employee (name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user, status) " +
            "values(#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser}, #{status})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);

    // 分页查询员工数据
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    // 根据主键动态修改数据
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    // 根据ID查询员工信息
    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
