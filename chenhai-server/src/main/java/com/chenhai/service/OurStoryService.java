package com.chenhai.service;

import com.chenhai.entity.OurStoryColumn;
import com.chenhai.vo.OurStoryPostVO;

import java.util.List;

/**
 * 获取我的故事专栏列表
 * @return
 */
public interface OurStoryService {
    /**
     * 获取我的故事专栏列表
     * @return
     */
    List<OurStoryColumn> getColumnList();

    /**
     * 获取我的故事帖子列表
     * @return
     */
    List<OurStoryPostVO> getPostList();
}
