package org.xyx.redis.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RedisLockAspect {

    @Around("@annotation(org.xyx.redis.utils.RedisLock)")
    public void around(ProceedingJoinPoint point) {
        System.out.println("aspect in");
        try {
            point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("aspect out");
    }

}
