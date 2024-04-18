package com.sky.service.community.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sky.context.BaseContext;
import com.sky.dto.community.CommunityPostDTO;
import com.sky.entity.User;
import com.sky.entity.community.CommunityPost;
import com.sky.mapper.UserMapper;
import com.sky.mapper.community.PostMapper;
import com.sky.service.community.PostService;
import com.sky.vo.community.CommunityPostVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            communityPost.setPostId(String.valueOf(communityPost.getId()));
            communityPost.setId(null);
            if (communityPost.getImageUrls() != null) {
                List<String> arrayList = JSONObject.parseArray(JSONObject.toJSON(communityPost.getImageUrls()).toString(), String.class);
                communityPost.setImgUrlList(arrayList);
            }
            User user = userMapper.selectById(communityPost.getUserId());
            if (user != null) {
                communityPost.setAvatarUrl(user.getAvatar());
                communityPost.setName(user.getName());
                communityPost.setActiveStatus(user.getActiveStatus());
            }
        }

        return list;
    }

    /**
     * 发布帖子
     * @param communityPostDTO
     */
    public void publishPost(CommunityPostDTO communityPostDTO) {
        CommunityPost post = new CommunityPost();
        BeanUtils.copyProperties(communityPostDTO, post);
        post.setImageUrls(JSONObject.toJSONString(communityPostDTO.getImageUrlsD()));
        post.setCreatedTime(LocalDateTime.now());
        post.setUpdatedTime(LocalDateTime.now());
        postMapper.insert(post);
    }

    /**
     * 根据ID查询帖子详情
     * @param id
     * @return
     */
    public CommunityPostVO getPostById(Long id) {
        QueryWrapper<CommunityPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        CommunityPost communityPost = postMapper.selectOne(queryWrapper);
        CommunityPostVO communityPostVO = new CommunityPostVO();
        BeanUtils.copyProperties(communityPost, communityPostVO);
        communityPostVO.setPostId(String.valueOf(communityPost.getId()));
        if (communityPost.getImageUrls() != null) {
            List<String> arrayList = JSONObject.parseArray(JSONObject.toJSON(communityPost.getImageUrls()).toString(), String.class);
            communityPostVO.setImgUrlList(arrayList);
        }

        return communityPostVO;
    }

    /**
     * 帖子点赞 type = 1 点赞 type = 0 取消点赞
     * @param type
     */
    public void thumb(Integer type, String postId) {
        Long userId = BaseContext.getCurrentId();

        CommunityPost communityPost = postMapper.selectById(Long.valueOf(postId));
        communityPost.setThumb(communityPost.getThumb() + 1);
        User user = userMapper.selectById(userId);
        user.setThumb(user.getThumb() + 1);

        userMapper.updateById(user);
        postMapper.updateById(communityPost);
    }
}
