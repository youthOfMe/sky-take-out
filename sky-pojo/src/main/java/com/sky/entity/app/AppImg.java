package com.sky.entity.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppImg {

    // id
    private Long id;

    // 图片连接
    private String imageUrl;

    // 图片类型
    private Integer type;

    // 创建时间
    private LocalDateTime createdTime;
}
