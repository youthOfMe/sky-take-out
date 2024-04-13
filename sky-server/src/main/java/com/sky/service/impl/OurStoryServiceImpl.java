package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.entity.OurStoryColumn;
import com.sky.mapper.OurStoryColumnMapper;
import com.sky.mapper.OurStoryPostMapper;
import com.sky.service.OurStoryService;
import com.sky.vo.OurStoryPostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OurStoryServiceImpl implements OurStoryService {

    @Autowired
    private OurStoryColumnMapper ourStoryColumnMapper;

    @Autowired
    private OurStoryPostMapper ourStoryPostMapper;

    /**
     * 获取我的故事专栏列表
     * @return
     */
    public List<OurStoryColumn> getColumnList() {
        return ourStoryColumnMapper.selectList(null);
    }

    /**
     * 获取我的故事帖子列表
     * @return
     */
    public List<OurStoryPostVO> getPostList() {
        List<OurStoryPostVO> list = ourStoryPostMapper.selectAllList();
        for (OurStoryPostVO ourStoryPostVO : list) {
            List<String> arrayList = JSONObject.parseArray(ourStoryPostVO.getImageUrlList(), String.class);
            ourStoryPostVO.setImgUrlList(arrayList);
        }
        return list;
    }
}
