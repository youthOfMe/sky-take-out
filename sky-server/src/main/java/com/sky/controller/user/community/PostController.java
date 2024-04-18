package com.sky.controller.user.community;

import com.sky.entity.community.CommunityPost;
import com.sky.result.Result;
import com.sky.service.community.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<List<CommunityPost>> list(Long categoryId) {
        List<CommunityPost> list = postService.list(categoryId);
        return Result.success(list);
    }
}
