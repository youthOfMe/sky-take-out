package com.sky.service.community;

import com.sky.dto.community.CommunityPostDTO;
import com.sky.entity.community.CommunityPost;
import com.sky.vo.community.CommunityPostVO;

import java.util.List;

public interface PostService {

    /**
     * 根据板块ID获取帖子数据
     * @param categoryId
     * @return
     */
    List<CommunityPost> list(Long categoryId, Integer recommended, Long userId);

    /**
     * 用户发布帖子
     * @param communityPost
     */
    void publishPost(CommunityPostDTO communityPostDTO);

    /**
     * 根据ID查询帖子详情
     * @param id
     * @return
     */
    CommunityPostVO getPostById(Long id);

    /**
     * 帖子点赞
     * @param type
     * @param postId
     */
    void thumb(Integer type, String postId);

    /**
     * 判断是否进行点在了
     * @param postId
     * @return
     */
    Boolean isThumbByUserId(String postId);

    /**
     * 根据用户ID获取帖子列表
     * @return
     */
    List<CommunityPost> getPostByUserId();
}
