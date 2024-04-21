package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable {

    // 主键ID
    private Long id;

    // 标签名称
    private String TagName;

    // 用户ID
    private Long userId;

    // 父节点ID
    private Long parentId;

    // 是否为父节点
    private Integer isParent;

    // 创建时间
    private LocalDateTime createdTime;

    // 更新时间
    private LocalDateTime updatedTime;

    // 是否被删除
    private Integer deleted;
}
