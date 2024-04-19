package com.sky.mapper.app;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.app.AppImg;
import org.springframework.stereotype.Repository;

@Repository("userAppImgMapper")
public interface AppImgMapper extends BaseMapper<AppImg> {
}
