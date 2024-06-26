package com.chenhai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.chenhai.dto.user.UserPageQueryDTO;
import com.chenhai.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
* 根据openid查询用户
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid};")
    User getByOpenid(String openid);

    /**
     * 插入数据
     * @param user
     */
    // void insertUser(User user);

    /**
     * 根据用户Id查询用户数据
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getById(Long userId);

    /**
     * 根据动态条件进行统计用户数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);

    /**
     * 分页查询
     * @param userPageQueryDTO
     * @return
     */
    Page<User> pageQuery(UserPageQueryDTO userPageQueryDTO);
}
