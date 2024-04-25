package com.chenhai.controller.user;

import com.chenhai.constant.StatusConstant;
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


@CrossOrigin(origins = "*")
@RestController("userDishController")
@RequestMapping("/user/commodity")
@Slf4j
@Api(tags = "C端-商品浏览接口")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询商品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> list(Long categoryId) {

        // 构造redis中的key 规则: dish_分类id
        String key = "dish_" + categoryId;

        // 查询redis是否存在菜品数据
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if (list != null && list.size() > 0) {
            // 如果存在 直接返回无需查询数据库
            return Result.success(list);
        }

        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        // 查询起售中的菜品
        dish.setStatus(StatusConstant.ENABLE);

        // 如果不存在 查询数据库 将查询到的数据存入redis中
        list = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key, list);

        return Result.success(list);
    }

    /**
     * 菜品的分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page (DishPageQueryDTO dishPageQueryDTO) {
        log.info("用户端菜品分页查询");
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
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
}
