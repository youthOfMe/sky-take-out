package com.chenhai.entity.community;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityCategory {

    // id
    private Long id;

    // 创建者
    private Long userId;

    // 管理者
    private Long managerId;

    // 归属分类
    private Long parentId;

    // 名称
    private String name;

    // 描述
    private String description;

    // 封面图片
    private String coverUrl;

    // 排序
    private Integer sort;

    // 是否推荐
    private Integer recommended;

    // 创建时间
    private LocalDateTime createdTime;

    // 更新时间
    private LocalDateTime updatedTime;

    // 是否逻辑删除
    @TableLogic
    private Integer deleted;
}
