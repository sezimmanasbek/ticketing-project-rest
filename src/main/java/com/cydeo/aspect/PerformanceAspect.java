package com.cydeo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class PerformanceAspect {

    Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

    @Pointcut("@annotation(com.cydeo.annotation.ExecutionTime)")
    public void anyExecutionTimeOperation(){}

    @Around("anyExecutionTimeOperation()")
    public Object aroundAnyExecutionTimeOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        long beforeTime = System.currentTimeMillis();


       Object object = joinPoint.proceed();

        long afterTime = System.currentTimeMillis();
        logger.info("Time taken to execute: {} ms -  Method:{} - Parameters: {}"
                ,(afterTime - beforeTime),joinPoint.getSignature().toShortString(),joinPoint.getArgs());
        return object;

    }
}
