package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OurStoryPost {
    private Long id;

    private Long userId;

    private String title;

    private String content;

    private Integer ourStoryColumn;

    private Integer thumbNumber;

    private Integer collectionNumber;

    private String coverUrl;

    private String imageUrlList;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
