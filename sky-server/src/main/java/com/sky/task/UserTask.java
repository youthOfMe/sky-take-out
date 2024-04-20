package com.sky.task;

import com.sky.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserTask {

    @Autowired
    UserMapper userMapper;

    /**
     * 处理签到
     */
    // @Scheduled(cron = "0 0  * * ?")
    // public void processDeliveryOrder() {
    //     log.info("每天零点定时处理所有人签到: {}", LocalDateTime.now());
    //
    //
    //
    //     userMapper.update();
    // }
}
