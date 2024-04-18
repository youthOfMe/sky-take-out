package com.sky.service.community;

import com.sky.entity.community.CommunityPost;

import java.util.List;

public interface PostService {

    /**
     * 根据板块ID获取帖子数据
     * @param categoryId
     * @return
     */
    List<CommunityPost> list(Long categoryId);
}
