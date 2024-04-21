package com.chenhai.config;

import com.chenhai.properties.AliSmsProperties;
import com.chenhai.utils.AliSmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AliSmsConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AliSmsUtil aliSmsUtil(AliSmsProperties aliSmsProperties) {
        log.info("开始创建阿里云短信发送工具类对象: {}", aliSmsProperties);
        return new AliSmsUtil(aliSmsProperties.getSignName(), aliSmsProperties.getTemplateCode()
            , aliSmsProperties.getAccessKey(), aliSmsProperties.getSecret());
    }
}
