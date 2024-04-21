package com.chenhai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenhai.entity.OurStoryPost;
import com.chenhai.vo.OurStoryPostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OurStoryPostMapper extends BaseMapper<OurStoryPost> {
    @Select("select post.id, post.user_id, post.title, post.content, post.our_story_column, post.thumb_number, post.collection_number" +
            ", post.cover_url, post.image_url_list, post.create_time, post.update_time" +
            ", user.name, user.avatar from our_story_post post left join user on post.user_id = user.id")
    List<OurStoryPostVO> selectAllList();
}
