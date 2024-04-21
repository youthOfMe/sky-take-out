package com.chenhai.mapper.community;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenhai.entity.community.CommunityParentCategory;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryParentMapper extends BaseMapper<CommunityParentCategory> {

    @Select("select id, name, description, created_time, update_time, sort from community_parent_category")
    List<CommunityParentCategory> list();
}
