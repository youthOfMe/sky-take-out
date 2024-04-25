package com.chenhai.controller.user.app;

import com.chenhai.entity.app.AppImg;
import com.chenhai.result.Result;
import com.chenhai.service.app.AppImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController("userAppImgController")
@RequestMapping("/user/app/img")
@Api(tags = "C端应用配置相关接口")
@Slf4j
public class AppImgController {

    @Autowired
    private AppImgService appImgService;

    /**
     * 获取APP图片根据类型
     * @param type
     * @return
     */
    @GetMapping("/img")
    @ApiOperation("获取图片列表")
    public Result<List<AppImg>> img(Integer type) {
        List<AppImg> list = appImgService.getImgListByType(type);
        return Result.success(list);
    }
}
