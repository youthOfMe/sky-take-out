package com.chenhai.mapper.community;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenhai.entity.community.CommunityCategory;
import org.springframework.stereotype.Repository;

@Repository("userCommunityCategory")
public interface CategoryMapper extends BaseMapper<CommunityCategory> {
}
