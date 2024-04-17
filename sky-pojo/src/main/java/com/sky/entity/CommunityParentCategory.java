package com.sky.entity;

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
public class CommunityParentCategory {

    // id
    private Long id;

    // 名称
    private String name;

    // 描述
    private String describe;

    // 排序
    private Integer sort;

    // 创建时间
    private LocalDateTime created_time;

    // 更新时间
    private LocalDateTime updated_time;

    // 是否逻辑删除
    @TableLogic
    private Integer delete;
}
