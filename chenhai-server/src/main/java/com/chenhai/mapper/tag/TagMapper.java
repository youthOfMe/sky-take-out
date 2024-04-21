package com.chenhai.mapper.tag;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenhai.entity.Tag;
import org.springframework.stereotype.Repository;

@Repository("userTagMapper")
public interface TagMapper extends BaseMapper<Tag> {
}
