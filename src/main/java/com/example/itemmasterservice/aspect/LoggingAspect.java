package com.example.itemmasterservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.example.itemmasterservice.controller.*.*(..))")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        logger.info("Starting execution of {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

        Object proceed;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("Exception in {}.{} with cause = {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), throwable.getCause() != null ? throwable.getCause() : "NULL");
            throw throwable;
        }

        long executionTime = System.currentTimeMillis() - start;
        logger.info("Completed execution of {}.{} in {} ms", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), executionTime);

        return proceed;
    }
}