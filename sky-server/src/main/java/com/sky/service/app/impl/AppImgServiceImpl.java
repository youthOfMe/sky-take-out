package com.sky.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sky.entity.app.AppImg;
import com.sky.mapper.app.AppImgMapper;
import com.sky.service.app.AppImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userAppImgService")
public class AppImgServiceImpl implements AppImgService {

    @Autowired
    private AppImgMapper appImgMapper;

    /**
     * 获取APP图片根据类型
     * @param type
     * @return
     */
    public List<AppImg> getImgListByType(Integer type) {
        QueryWrapper<AppImg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);

        List<AppImg> list = appImgMapper.selectList(queryWrapper);

        return list;
    }
}
