package org.example.yy.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.yy.exception.AccessException;
import org.example.yy.exception.ErrorCode;
import org.example.yy.util.EncDecUtil;
import org.example.yy.web.api.RoleInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Slf4j
@Component
public class RoleInterceptor implements HandlerInterceptor, WebMvcConfigurer {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        Access access = handlerMethod.getMethod().getAnnotation(Access.class);

        if (access == null || access.roles() == null ||access.roles().length == 0) {
            log.error("@Access missing in {}", handlerMethod.getMethod().getName());
            throw new AccessException(ErrorCode.REMOTE_SERVER_ERROR);
        }

        String roleInfoEncoded = request.getHeader("RoleInfo");
        if (!StringUtils.hasText(roleInfoEncoded)) {
            throw new AccessException(ErrorCode.ROLE_INFO_MISSING);
        }
        RoleInfo roleInfo = null;
        try {
           roleInfo = EncDecUtil.getRoleInfo(roleInfoEncoded);
        } catch (Exception e) {
            log.error("role info not correct.");
            throw new AccessException(ErrorCode.ROLE_INFO_NOT_CORRECT);
        }

        boolean isAccessed = Arrays.asList(access.roles()).contains(roleInfo.getRole());
        if (isAccessed) {
            return true;
        } else {
            log.error("access denied: {}", roleInfo);
            throw new AccessException(ErrorCode.ACCESS_DENIED);
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this);
    }
}
