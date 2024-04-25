package com.chenhai.controller.user.community;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenhai.context.BaseContext;
import com.chenhai.dto.community.CommunityPostCommitDTO;
import com.chenhai.entity.community.CommunityCommit;
import com.chenhai.mapper.community.CommitMapper;
import com.chenhai.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController("userCommunityCommitController")
@RequestMapping("/user/community/commit")
@Api(tags = "C端社区贴子评论相关接口")
@Slf4j
public class CommitController {

    @Autowired
    private CommitMapper commitMapper;

    /**
     * 发布帖子评论
     * @param communityPostCommitDTO
     * @return
     */
    @PostMapping("/post")
    @ApiOperation("发布帖子评论")
    public Result postCommit(@RequestBody CommunityPostCommitDTO communityPostCommitDTO) {
        Long userId = BaseContext.getCurrentId();
        CommunityCommit communityPostCommit = new CommunityCommit();
        communityPostCommit.setUserId(userId);
        communityPostCommit.setContent(communityPostCommitDTO.getCommitContent());
        communityPostCommit.setPostId(communityPostCommitDTO.getPostId());
        communityPostCommit.setCreatedTime(LocalDateTime.now());
        commitMapper.insert(communityPostCommit);
        return Result.success();
    }

    /**
     * 获取帖子的评论列表
     * @param postId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("获取帖子评论列表")
    public Result<List<CommunityCommit>> getPostCommitList(Long postId) {
        QueryWrapper<CommunityCommit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", postId);
        List<CommunityCommit> communityCommits = commitMapper.selectList(queryWrapper);
        return Result.success(communityCommits);
    }
}
