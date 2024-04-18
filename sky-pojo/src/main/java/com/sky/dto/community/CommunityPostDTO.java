package com.sky.dto.community;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CommunityPostDTO {

    // 发帖人
    private Long userId;

    // 标题
    private String title;

    // 内容
    private String content;

    // 归属板块
    private Long categoryId;

    // 归属专栏
    private Long columnId;

    // 发帖人名称
    private String name;

    // 发帖人头像
    private String avatarUrl;

    // 帖子封面
    private String coverUrl;

    // 帖子图片
    private ArrayList<String> imageUrlsD;
}
