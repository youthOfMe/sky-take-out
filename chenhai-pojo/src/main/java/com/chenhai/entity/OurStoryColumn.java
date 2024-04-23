package com.chenhai.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 我们的故事的专栏
 * @TableName our_story_column
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OurStoryColumn implements Serializable {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 专栏名称
     */
    private String name;

    /**
     * 专栏描述
     */
    private String nameDesc;

    /**
     * 创建者 老大 主键关联
     */
    private Long createdUser;

    /**
     * 帖子数量
     */
    private Integer postNumber;

    /**
     * 点赞数
     */
    private Integer thumbNumber;

    /**
     * 收藏数量
     */
    private Integer collectionNumber;

    /**
     * 封面
     */
    private String coverUrl;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}