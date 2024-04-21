package com.sky.mapper.tag;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.Tag;
import org.springframework.stereotype.Repository;

@Repository("userTagMapper")
public interface TagMapper extends BaseMapper<Tag> {
}
