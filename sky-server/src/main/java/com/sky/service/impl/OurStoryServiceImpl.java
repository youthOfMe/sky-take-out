package com.sky.service.impl;

import com.sky.entity.OurStoryColumn;
import com.sky.mapper.OurStoryMapper;
import com.sky.service.OurStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OurStoryServiceImpl implements OurStoryService {

    @Autowired
    private OurStoryMapper ourStoryMapper;

    /**
     * 获取我的故事专栏列表
     * @return
     */
    public List<OurStoryColumn> getColumnList() {
        return ourStoryMapper.selectList(null);
    }
}
