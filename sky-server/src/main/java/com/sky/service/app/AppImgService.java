package com.sky.service.app;

import com.sky.entity.app.AppImg;

import java.util.List;

public interface AppImgService {

    /**
     * 获取APP图片根据类型
     * @param type
     * @return
     */
    List<AppImg> getImgListByType(Integer type);
}
