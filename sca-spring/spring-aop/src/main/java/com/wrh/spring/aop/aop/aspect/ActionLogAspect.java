package com.wrh.spring.aop.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.util.RamUsageEstimator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ActionLogAspect {

    @Autowired
    private HttpServletRequest request;

    private static final ThreadLocal<Long> REQUEST_START_TIME = new ThreadLocal<>();

    @Pointcut("execution( * com.wrh.spring.aop.controller.*.*(..))")
    public void actionLogPointCut() {
    }

//    @Before("actionLogPointCut()")
//    public void doBefore(JoinPoint joinPoint) {
//
//    }

//    @AfterReturning(returning = "ret", pointcut = "actionLogPointCut()")
//    public void doAfterReturning(Object ret) {
//
//        log.info("-----------------------------");
//    }

    @Around("actionLogPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Request ——— ");
        String url = this.request.getRequestURI();
        String requestMethod = this.request.getMethod();
        String addr = this.request.getRemoteAddr();
        Signature signature = joinPoint.getSignature();
        String clazzMethod = signature.getDeclaringTypeName() + "." + signature.getName();
        REQUEST_START_TIME.set(System.currentTimeMillis());
        log.info("URL: {} ", url);
        log.info("HTTP_METHOD: {}", requestMethod);
        log.info("IP: {}", addr);
        log.info("CLASS_METHOD: {}", clazzMethod);
        log.info("ARGS: {} ", Arrays.toString(joinPoint.getArgs()));
        Object result = null;
        try {
            result = joinPoint.proceed();
            long size = RamUsageEstimator.sizeOf(result);
            long costTime = System.currentTimeMillis() - REQUEST_START_TIME.get();
            log.info("Response ——— 耗时: {}ms, 返回数据大小: {}字节 ——— ", costTime, size);
        } catch (Exception ex) {
            log.error("Response Error ——— " + url, ex);
        }
        return result;
    }


}
