package com.cydeo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Configuration
@Aspect
public class LoggingAspect {

    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);


    public String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount details = (SimpleKeycloakAccount) authentication.getDetails();
        return details.getKeycloakSecurityContext().getToken().getPreferredUsername();
    }

    @Pointcut("execution(* com.cydeo.controller.ProjectController.*(..)) || execution(* com.cydeo.controller.TaskController.*(..))")
    public void anyControllerOperation(){}


    @Before("anyControllerOperation()")
    public void anyBeforeControllerOperation(JoinPoint joinPoint){
        String username = getUsername();
        logger.info("Before () -> User: {} Method: {} Paramaters: {}",username,joinPoint.getSignature().toShortString(),joinPoint.getArgs());

    }

    @AfterReturning(pointcut = "anyControllerOperation()",returning = "results")
    public void anyAfterControllerOperation(JoinPoint joinPoint, Object results){
        logger.info("AfterReturning ->  User: {} Method: {} Parameters: {}",getUsername(),joinPoint.getSignature().toShortString(),joinPoint.getArgs());

    }

    @AfterThrowing(pointcut = "anyControllerOperation()",throwing = "exception")
    public void anyAfterThrowingControllerOperation(JoinPoint joinPoint, RuntimeException exception){
        logger.info("AfterThrowing ->  User: {} Method: {} Exception: {}",getUsername(),joinPoint.getSignature().toShortString(),exception.getMessage());

    }
}
