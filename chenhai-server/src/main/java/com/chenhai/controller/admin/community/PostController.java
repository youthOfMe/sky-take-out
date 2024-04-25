package com.chenhai.controller.admin.community;

import com.chenhai.entity.community.CommunityPost;
import com.chenhai.result.Result;
import com.chenhai.service.community.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController("adminCommunityPostController")
@RequestMapping("/admin/community/post")
@Api(tags = "用户管理端社区帖子相关接口")
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
}
