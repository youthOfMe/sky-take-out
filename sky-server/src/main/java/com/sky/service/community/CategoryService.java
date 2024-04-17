package com.sky.service.community;

import com.sky.entity.community.CommunityParentCategory;

import java.util.List;

public interface CategoryService {
    /**
     * 一级分类板块查询
     * @return
     */
    List<CommunityParentCategory> parentCategory();
}
