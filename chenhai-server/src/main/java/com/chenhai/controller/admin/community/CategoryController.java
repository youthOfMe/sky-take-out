package com.chenhai.controller.admin.community;

import com.chenhai.entity.community.CommunityCategory;
import com.chenhai.entity.community.CommunityParentCategory;
import com.chenhai.result.Result;
import com.chenhai.service.community.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("adminCommunityCategoryController")
@RequestMapping("/admin/community/category")
@Api(tags = "管理端管理社区分类相关接口")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 一级分类板块查询
     * @return
     */
    @GetMapping("/parentList")
    @ApiOperation("一级分类板块查询")
    public Result<List<CommunityParentCategory>> parentCategoryList() {
        log.info("用户查询一级分类");
        List<CommunityParentCategory> list = categoryService.parentCategory();
        return Result.success(list);
    }

    /**
     * 社区本快查询
     * @param parentId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("板块查询")
    public Result<List<CommunityCategory>> categoryList(Long parentId, Integer recommended) {
        log.info("社区板块查询");
        List<CommunityCategory> list = categoryService.list(null);
        return Result.success(list);
    }


}
