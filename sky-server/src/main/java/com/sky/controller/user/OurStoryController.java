package com.sky.controller.user;

import com.sky.entity.OurStoryColumn;
import com.sky.entity.OurStoryPost;
import com.sky.result.Result;
import com.sky.service.OurStoryService;
import com.sky.vo.OurStoryPostVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("ourStoryController")
@RequestMapping("/user/ourstory")
@Api(tags = "我们的故事专栏相关的接口")
@Slf4j
public class OurStoryController {

    @Autowired
    OurStoryService ourStoryService;

    /**
     * 获取我的故事专栏列表
     * @return
     */
    @GetMapping("/columnlist")
    @ApiOperation("获取我的故事专栏列表")
    public Result<List<OurStoryColumn>> getColumnList() {
        List<OurStoryColumn> ourStoryColumns = ourStoryService.getColumnList();
        return Result.success(ourStoryColumns);
    }

    /**
     * 获取我的故事帖子列表
     * @return
     */
    @GetMapping("/postlist")
    @ApiOperation("获取我的故事帖子列表")
    public Result<List<OurStoryPostVO>> getPostList() {
        List<OurStoryPostVO> ourStoryPosts = ourStoryService.getPostList();
        return Result.success(ourStoryPosts);
    }


}
