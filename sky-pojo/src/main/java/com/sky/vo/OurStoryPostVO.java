package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OurStoryPostVO implements Serializable {
    private Long id;

    private Long userId;

    private String title;

    private String content;

    private Integer ourStoryColumn;

    private Integer thumbNumber;

    private Integer collectionNumber;

    private String coverUrl;

    private String imageUrlList;

    private List<String> imgUrlList;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String name;

    private String avatar;
}
