package com.chenhai.controller.user.community;

import com.chenhai.dto.community.CommunityPostDTO;
import com.chenhai.entity.community.CommunityPost;
import com.chenhai.result.Result;
import com.chenhai.service.community.PostService;
import com.chenhai.vo.community.CommunityPostVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userCommunityPostController")
@RequestMapping("/user/community/post")
@Api(tags = "C端社区帖子相关接口")
@Slf4j
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * 根据板块ID获取帖子数据
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据板块ID回去帖子数据")
    public Result<List<CommunityPost>> list(Long categoryId, Integer recommended, Long userId) {
        List<CommunityPost> list = postService.list(categoryId, recommended, userId);
        return Result.success(list);
    }

    /**
     * 用户发布帖子
     * @param communityPostDTO
     * @return
     */
    @PostMapping("/post")
    @ApiOperation("用户发布帖子")
    public Result publishPost(@RequestBody CommunityPostDTO communityPostDTO) {
        postService.publishPost(communityPostDTO);
        return Result.success();
    }

    /**
     * 根据ID查询帖子详情
     * @param id
     * @return
     */
    @GetMapping("/post")
    @ApiOperation("根据ID获取帖子详细")
    public Result<CommunityPostVO> getPostById(String id) {
        Long postId = Long.valueOf(id);
        CommunityPostVO communityPost = postService.getPostById(postId);
        return Result.success(communityPost);
    }

    /**
     * 判断是否进行点在了
     * @param postId
     * @return
     */
    @GetMapping("/isThumb")
    @ApiOperation("判断用户是否对帖子进行点赞了")
    public Result<Boolean> isThumbByUserId(String postId) {
        Boolean isThumb = postService.isThumbByUserId(postId);
        return Result.success(isThumb);
    }

    /**
     * 帖子点赞
     * @param type
     * @return
     */
    @PostMapping("/thumb")
    @ApiOperation("帖子点赞")
    public Result thumb(Integer type, String postId) {
        postService.thumb(type, postId);
        return Result.success();
    }

    /**
     * 根据用户ID获取帖子列表
     * @return
     */
    @GetMapping("/postByUserId")
    @ApiOperation("根据用户ID获取帖子列表")
    public Result<List<CommunityPost>> getPostListByUserId() {
        List<CommunityPost> list = postService.getPostByUserId();
        return Result.success(list);
    }
}
