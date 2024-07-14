package com.belaquaa.jwt.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
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

    @AfterThrowing(pointcut = "execution(* com.belaquaa.jwt.controller..*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        String userId = AuthenticationContextHolder.getUserInfo().getUserId();
        if (ex instanceof AccessDeniedException) {
            log.error("Access Denied error for user with id {}: {}", userId, ex.getMessage());
        } else {
            log.error("An error occurred for user with id {}: {}", userId, ex.getMessage());
        }
    }
}
