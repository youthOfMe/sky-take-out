package com.chenhai.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Component
public class IPInterceptor implements HandlerInterceptor {

    private List<String> allowedIps;

    public IPInterceptor() {
        // 从配置中获取IP白名单
        allowedIps = Arrays.asList("49.232.17.169");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = request.getRemoteAddr();
        return allowedIps.contains(clientIp);
    }
}
