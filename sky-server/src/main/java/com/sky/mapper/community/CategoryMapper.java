package com.sky.mapper.community;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.community.CommunityCategory;
import org.springframework.stereotype.Repository;

@Repository("userCommunityCategory")
public interface CategoryMapper extends BaseMapper<CommunityCategory> {
}
