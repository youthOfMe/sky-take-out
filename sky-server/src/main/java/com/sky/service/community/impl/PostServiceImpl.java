package com.sky.service.community.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sky.entity.User;
import com.sky.entity.community.CommunityPost;
import com.sky.mapper.UserMapper;
import com.sky.mapper.community.PostMapper;
import com.sky.service.community.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userCommunityPostService")
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据板块ID获取帖子数据
     * @param categoryId
     * @return
     */
    public List<CommunityPost> list(Long categoryId) {
        QueryWrapper<CommunityPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryId);
        List<CommunityPost> list = postMapper.selectList(queryWrapper);
        for (CommunityPost communityPost : list) {
            User user = userMapper.selectById(communityPost.getUserId());
            if (user != null) {
                communityPost.setAvatarUrl(user.getAvatar());
                communityPost.setName(user.getName());
            }
        }

        return list;
    }
}
