package com.chenhai.service.chart.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenhai.entity.chart.Chart;
import com.chenhai.mapper.chart.ChartMapper;
import com.chenhai.service.chart.ChartService;
import org.springframework.stereotype.Service;

@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
        implements ChartService {
}
