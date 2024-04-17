package com.sky.service.community.impl;

import com.sky.entity.community.CommunityParentCategory;
import com.sky.mapper.community.CategoryParentMapper;
import com.sky.service.community.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userCommunityCategoryService")
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryParentMapper categoryParentMapper;

    /**
     * 一级分类板块查询
     * @return
     */
    public List<CommunityParentCategory> parentCategory() {
        return categoryParentMapper.list();
    }
}
