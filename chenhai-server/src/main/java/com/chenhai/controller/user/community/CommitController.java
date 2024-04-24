package com.chenhai.controller.user.community;

import com.chenhai.context.BaseContext;
import com.chenhai.dto.community.CommunityPostCommitDTO;
import com.chenhai.entity.community.CommunityCommit;
import com.chenhai.mapper.community.CommitMapper;
import com.chenhai.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
}
