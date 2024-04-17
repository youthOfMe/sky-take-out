package com.sky.service.community;

import com.sky.entity.community.CommunityCategory;
import com.sky.entity.community.CommunityParentCategory;

import java.util.List;

public interface CategoryService {
    /**
     * 一级分类板块查询
     * @return
     */
    List<CommunityParentCategory> parentCategory();

    /**
     * 社区本快查询
     * @param parentId
     * @return
     */
    List<CommunityCategory> category(Long parentId, Integer recommended);
}
