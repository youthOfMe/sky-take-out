package com.chenhai.entity.community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityPostCommit {

    // id
    private Long id;

    // 创建者
    private Long userId;

    // 内容
    private String content;

    // 帖子ID
    private Long postId;

    // 回复帖子ID
    private Long parentId;

    // 评论时间
    private LocalDateTime createdTime;
}
