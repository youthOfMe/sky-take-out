package com.chenhai.service.community.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenhai.context.BaseContext;
import com.chenhai.dto.community.CommunityPostDTO;
import com.chenhai.entity.User;
import com.chenhai.entity.community.CommunityPost;
import com.chenhai.entity.community.ThumbPostUser;
import com.chenhai.mapper.UserMapper;
import com.chenhai.mapper.community.PostMapper;
import com.chenhai.mapper.community.ThumbPostUserMapper;
import com.chenhai.service.community.PostService;
import com.chenhai.vo.community.CommunityPostVO;
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

    @Autowired
    private ThumbPostUserMapper thumbPostUserMapper;

    /**
     * 根据板块ID获取帖子数据
     * @param categoryId
     * @return
     */
    public List<CommunityPost> list(Long categoryId, Integer recommended, Long userId) {
        log.info("查询帖子列表: {}, {}", categoryId, recommended);
        QueryWrapper<CommunityPost> queryWrapper = new QueryWrapper<>();
        if (categoryId != null) {
            queryWrapper.eq("category_id", categoryId);
        }
        if (recommended != null) {
            queryWrapper.eq("recommended", recommended);
        }
        if (userId != null) {
            queryWrapper.eq("user_id", userId);
        }
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
     * 获取点赞的用户列表
     * @param postId
     * @return
     */
    // public List<String> thumb_list(String postId) {
    //
    // }




    /**
     * 帖子点赞 type = 1 点赞 type = 0 取消点赞
     * @param type
     */
    public void thumb(Integer type, String postId) {
        Long userId = BaseContext.getCurrentId();

        CommunityPost communityPost = postMapper.selectById(Long.valueOf(postId));
        User user = userMapper.selectById(userId);
        ThumbPostUser thumbPostUser = new ThumbPostUser();
        thumbPostUser.setPostId(postId);
        thumbPostUser.setUserId(String.valueOf(userId));
        thumbPostUser.setThumbTime(LocalDateTime.now());
        if (type == 1) {
            communityPost.setThumb(communityPost.getThumb() + 1);
            user.setThumb(user.getThumb() + 1);
            thumbPostUserMapper.insert(thumbPostUser);
        } else {
            communityPost.setThumb(communityPost.getThumb() - 1);
            user.setThumb(user.getThumb() - 1);
            QueryWrapper<ThumbPostUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", String.valueOf(userId));
            queryWrapper.eq("post_id", postId);
            thumbPostUser = thumbPostUserMapper.selectOne(queryWrapper);
            thumbPostUserMapper.deleteById(thumbPostUser.getId());
        }


        userMapper.updateById(user);
        postMapper.updateById(communityPost);
    }

    /**
     * 判断是否进行点在了
     * @param postId
     * @return
     */
    public Boolean isThumbByUserId(String postId) {
        Long userId = BaseContext.getCurrentId();
        QueryWrapper<ThumbPostUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", postId);
        queryWrapper.eq("user_id", String.valueOf(userId));
        log.info("niubi: {}", postId);

        return thumbPostUserMapper.selectOne(queryWrapper) != null;
    }

    /**
     * 根据用户ID获取帖子列表
     * @return
     */
    public List<CommunityPost> getPostByUserId() {
        Long userId = BaseContext.getCurrentId();

        QueryWrapper<CommunityPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<CommunityPost> list = postMapper.selectList(queryWrapper);
        return list;
    }
}
