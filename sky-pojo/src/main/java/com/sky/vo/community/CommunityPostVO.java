package com.sky.vo.community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityPostVO {
    // id
    private String postId;

    // 创建者
    private Long userId;

    // 标题
    private String title;

    // 内容
    private String content;

    // 点赞量
    private Integer thumb;

    // 评论量
    private Integer commit;

    // 分享量
    private Integer share;

    // 帖子封面
    private String coverUrl;

    // 帖子标签
    private String labels;

    // 图片识别标签
    private String imageLabels;

    // 归属板块
    private Long categoryId;

    // 归属专栏
    private Long columnId;

    // 创建时间
    private LocalDateTime createdTime;

    // 更新时间
    private LocalDateTime updatedTime;

    // 发帖人头像
    private String avatarUrl;

    // 发帖人昵称
    private String name;

    // 博主的在线状态
    private Integer activeStatus;

    // 图片列表
    private List<String> imgUrlList;
}
