package com.sky.service.community.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sky.entity.community.CommunityCategory;
import com.sky.entity.community.CommunityParentCategory;
import com.sky.mapper.community.CategoryMapper;
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
