package com.sky.controller.admin.community;

import com.sky.service.community.PostService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminCommunityPostController")
@RequestMapping("/admin/community/post")
@Api(tags = "用户管理端社区帖子相关接口")
@Slf4j
public class PostController {

    @Autowired
    private PostService postService;


}
