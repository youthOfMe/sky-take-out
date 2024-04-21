package com.chenhai.mapper.community;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenhai.entity.community.CommunityPost;
import org.springframework.stereotype.Repository;

@Repository("userCommunityPostMapper")
public interface PostMapper extends BaseMapper<CommunityPost> {
}
