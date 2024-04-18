package com.sky.mapper.community;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.community.CommunityPost;
import org.springframework.stereotype.Repository;

@Repository("userCommunityPostMapper")
public interface PostMapper extends BaseMapper<CommunityPost> {
}
