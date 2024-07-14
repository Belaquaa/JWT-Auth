package com.belaquaa.jwt.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import com.belaquaa.jwt.security.AuthenticationContextHolder;

@Slf4j
@Component
@Aspect
public class LogEndpointAspect {

    @Before("execution(public * com.belaquaa.jwt.controller..*Controller*.*(..))")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        log.info("Endpoint with the name {} triggered by user with id {}", joinPoint.getSignature().getName(),
                AuthenticationContextHolder.getUserInfo().getUserId());
    }
}
