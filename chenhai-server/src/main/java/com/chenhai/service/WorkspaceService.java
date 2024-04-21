package com.chenhai.service;

import com.chenhai.vo.BusinessDataVO;
import com.chenhai.vo.DishOverViewVO;
import com.chenhai.vo.OrderOverViewVO;
import com.chenhai.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkspaceService {
    /**
     * 根据时间段统计营业数据
     * @param begin
     * @param end
     * @return
     */
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    /**
     * 查询订单管理数据
     * @return
     */
    OrderOverViewVO getOrderOverView();

    /**
     * 查询次啊品总览
     * @return
     */
    DishOverViewVO getDishOverView();

    /**
     * 查询套餐总览
     * @return
     */
    SetmealOverViewVO getSetmealOverView();
}
