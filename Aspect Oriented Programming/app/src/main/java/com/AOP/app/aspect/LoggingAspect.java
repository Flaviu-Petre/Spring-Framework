package com.AOP.app.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    
    @Pointcut("execution(* com.AOP.app.service.*.*(..))")
    public void serviceLayer() {}
    
    @Pointcut("execution(* com.AOP.app.repository.*.*(..))")
    public void repositoryLayer() {}

    @Pointcut("execution(* com.AOP.app.client.*.*(..))")
    public void clientLayer() {}

    @Pointcut("serviceLayer() || repositoryLayer() || clientLayer()")
    public void applicationLayer() {}

    
    @Before("applicationLayer()")
    public void logMethodEntry(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        logger.info("ENTERING: {}.{}() with arguments: {}",
                className, methodName, Arrays.toString(args));

        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                logger.debug("   Parameter[{}]: {} = {}", i,
                        args[i] != null ? args[i].getClass().getSimpleName() : "null",
                        args[i]);
            }
        } else {
            logger.debug("   No parameters");
        }
    }

    @AfterReturning(pointcut = "applicationLayer()", returning = "result")
    public void logMethodSuccess(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        logger.info("SUCCESS: {}.{}() executed successfully", className, methodName);

        // Log return value details
        if (result != null) {
            logger.debug("   Return type: {}", result.getClass().getSimpleName());
            logger.debug("   Return value: {}", result);

            // Special handling for collections
            if (result instanceof java.util.Collection) {
                java.util.Collection<?> collection = (java.util.Collection<?>) result;
                logger.debug("   Collection size: {}", collection.size());
            }
        } else {
            logger.debug("   Return value: null (void method)");
        }
    }

    @AfterThrowing(pointcut = "applicationLayer()", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Throwable exception) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        logger.error("EXCEPTION in {}.{}() with arguments: {}",
                className, methodName, Arrays.toString(args));
        logger.error("   Exception type: {}", exception.getClass().getSimpleName());
        logger.error("   Exception message: {}", exception.getMessage());

        // Log stack trace for debugging
        if (logger.isDebugEnabled()) {
            logger.debug("   Stack trace:", exception);
        }
    }

    @Around("serviceLayer()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();
        logger.info("TIMING: Starting execution of {}.{}()", className, methodName);

        try {
            Object result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("TIMING: {}.{}() executed in {} ms",
                    className, methodName, executionTime);

            if (executionTime > 1000) {
                logger.warn(" PERFORMANCE: {}.{}() took {} ms (>1s)",
                        className, methodName, executionTime);
            }

            return result;

        } catch (Throwable throwable) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.error("  TIMING: {}.{}() failed after {} ms",
                    className, methodName, executionTime);
            throw throwable;
        }
    }

    @Pointcut("execution(* com.AOP.app.service.*.create*(..)) || " +
            "execution(* com.AOP.app.service.*.get*(..)) || " +
            "execution(* com.AOP.app.service.*.getAll*(..)) || " +
            "execution(* com.AOP.app.service.*.update*(..)) || " +
            "execution(* com.AOP.app.service.*.delete*(..))")
    public void crudOperations() {}

    @After("crudOperations()")
    public void logCrudOperation(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        String operation = "UNKNOWN";
        if (methodName.startsWith("create")) {
            operation = "CREATE";
        } else if (methodName.startsWith("get")) {
            operation = "READ";
        } else if (methodName.startsWith("update")) {
            operation = "UPDATE";
        } else if (methodName.startsWith("delete")) {
            operation = "DELETE";
        }

        logger.info("CRUD: {} operation performed with data: {}",
                operation, Arrays.toString(args));
    }
}