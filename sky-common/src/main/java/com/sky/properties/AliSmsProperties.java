package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "sky.sms")
public class AliSmsProperties {
    private String signName;
    private String templateCode;
    private String accessKey;
    private String secret;
}
