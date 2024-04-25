package com.chenhai.controller.admin;

import com.chenhai.dto.DishDTO;
import com.chenhai.dto.DishPageQueryDTO;
import com.chenhai.entity.Dish;
import com.chenhai.result.PageResult;
import com.chenhai.result.Result;
import com.chenhai.service.DishService;
import com.chenhai.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜品管理
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "商品相关接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询商品")
    public Result<List<Dish>> list(Long categoryId) {
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增商品")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增商品: {}", dishDTO);
        dishService.saveWithFlavor(dishDTO);

        // 清理缓存数据
        cleanCache("dish_" + dishDTO.getCategoryId());
        return Result.success();
    }

    /**
     * 菜品的分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("商品分页查询")
    public Result<PageResult> page (DishPageQueryDTO dishPageQueryDTO) {
        log.info("商品分页查询");
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 菜品批量删除
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("商品批量删除")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("商品批量删除: {}", ids);
        dishService.deleteBatch(ids);

        // 将所有的菜品缓存数据清理掉, 所有以dish_开通的key
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 根据id查询菜品数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询商品数据")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询商品: {}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改商品")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改商品: {}", dishDTO);
        dishService.updateWithFlavor(dishDTO);

        // 将所有的菜品缓存数据清理掉, 所有以dish_开头的key
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 菜品的起售停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("商品的起售停售")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        dishService.startOrStop(status, id);

        // 将所有的菜品缓存数据清理吊 所有以dish_开头的key
        cleanCache("dish_*");

        return Result.success();
    }

    private void cleanCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
