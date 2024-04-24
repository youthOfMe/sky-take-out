package com.chenhai.dto.community;

import lombok.Data;

@Data
public class CommunityPostCommitDTO {

    // 评论帖子ID
    private Long postId;

    // 评论内容
    private String commitContent;
}
