package com.sky.service;

import com.sky.entity.OurStoryColumn;

import java.util.List;

/**
 * 获取我的故事专栏列表
 * @return
 */
public interface OurStoryService {
    List<OurStoryColumn> getColumnList();
}
