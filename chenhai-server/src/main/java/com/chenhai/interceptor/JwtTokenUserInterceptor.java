package com.chenhai.interceptor;

import com.chenhai.constant.JwtClaimsConstant;
import com.chenhai.context.BaseContext;
import com.chenhai.properties.JwtProperties;
import com.chenhai.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private JwtUtil jwtUtil;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_SCHEMA = "Bearer ";
    public static final String ATTRIBUTE_UID = "uid";
    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getUserTokenName());
        // String token = getToken(request);


        //2、校验令牌

        log.info("jwt校验:{}", token);

        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("当前员工id：", userId);
            BaseContext.setCurrentId(userId);
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            //4、不通过，响应401状态码
            response.setStatus(401);
            return false;
        }

        // Long validUid = jwtUtil.getValidUid(token);
        // if (Objects.nonNull(validUid)) {//有登录态
        //     BaseContext.setCurrentId(validUid);
        //     //3、通过，放行
        //     return true;
        // } else {
        //     //4、不通过，响应401状态码
        //     response.setStatus(401);
        //     return false;
        // }

        // log.info("当前员工id：{}", validUid);

    }

    // public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //     //获取用户登录token
    //     String token = getToken(request);
    //     Long validUid = jwtUtil.getValidUid(token);
    //     if (Objects.nonNull(validUid)) {//有登录态
    //         request.setAttribute(ATTRIBUTE_UID, validUid);
    //     } else {
    //         boolean isPublicURI = isPublicURI(request.getRequestURI());
    //         if (!isPublicURI) {//又没有登录态，又不是公开路径，直接401
    //             HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
    //             return false;
    //         }
    //     }
    //     MDC.put(MDCKey.UID, String.valueOf(validUid));
    //     return true;
    // }

    private boolean isPublicURI(String requestURI) {
        String[] split = requestURI.split("/");
        return split.length > 2 && "public".equals(split[3]);
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        return Optional.ofNullable(header)
                .filter(h -> h.startsWith(AUTHORIZATION_SCHEMA))
                .map(h -> h.substring(AUTHORIZATION_SCHEMA.length()))
                .orElse(null);
    }


}
