package com.chenhai.dto.chart;

import com.baomidou.mybatisplus.annotation.TableField;
import com.chenhai.result.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询请求
 *
 */
@Data
public class ChartQueryDTO extends PageRequest implements Serializable {

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表类型
     */
    @TableField(value = "chart_type")
    private String chartType;

    /**
     * 用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}