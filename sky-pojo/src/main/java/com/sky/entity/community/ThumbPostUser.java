package com.sky.entity.community;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThumbPostUser {

    // 主键ID
    @TableId(type= IdType.AUTO)
    private Long id;

    // 帖子ID
    private String postId;

    // 点赞者ID
    private String userId;

    // 点赞时间
    private LocalDateTime thumbTime;
}
