package com.chenhai.service.community.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenhai.entity.community.CommunityCategory;
import com.chenhai.entity.community.CommunityParentCategory;
import com.chenhai.mapper.community.CategoryMapper;
import com.chenhai.mapper.community.CategoryParentMapper;
import com.chenhai.service.community.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userCommunityCategoryService")
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryParentMapper categoryParentMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 一级分类板块查询
     * @return
     */
    public List<CommunityParentCategory> parentCategory() {
        return categoryParentMapper.list();
    }

    /**
     * 社区板块查询
     * @param parentId
     * @return
     */
    public List<CommunityCategory> category(Long parentId, Integer recommended) {
        QueryWrapper<CommunityCategory> queryWrapper = new QueryWrapper<>();
        if (recommended != null) {
            queryWrapper.eq("recommended", recommended);
        } else {
            queryWrapper.eq("parent_id", parentId);
        }

        List<CommunityCategory> list = categoryMapper.selectList(queryWrapper);

        return list;
    }
}
