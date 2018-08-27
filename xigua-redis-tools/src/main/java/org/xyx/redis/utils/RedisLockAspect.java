package org.xyx.redis.utils;

import com.maodou.operate.IOperateAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * redis 分布式锁切面
 *
 * @author xyx
 * @date 2018/8/23 18:11
 */
@Aspect
@Order(-2)
@Component
public class RedisLockAspect {

    private Logger logger = LoggerFactory.getLogger(RedisLockAspect.class);

    @Resource
    private RedissonLock redissonLock;

    /**
     *
     * lock
     *
     * */
    @Around("@annotation(org.xyx.redis.utils.RedisLock)")
    public Object aroundLock(ProceedingJoinPoint point) throws Throwable {

        logger.info("[RedisLockAspect.aroundLock] in");

        RedisLock redisLock = getLockAnnotation(point, RedisLock.class);
        String key = redisLock.value();
        int leaseTime = redisLock.leaseTime();
        boolean fair = redisLock.fair();

        Object result = null;
        RLock lock = null;
        try {
            lock = redissonLock.lock(key, leaseTime, fair);
            if (lock != null) {
                result = point.proceed();
            }
        } catch (Throwable throwable) {
            logger.error("[RedisLockAspect.aroundLock] error occurred, key={}", key, throwable);
            throw throwable;
        } finally {
            if (lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
            logger.info("[RedisLockAspect.aroundLock] out");
        }
        return result;
    }

    /**
     *
     * tryLock
     *
     * */
    @Around("@annotation(org.xyx.redis.utils.RedisTryLock)")
    public Object aroundTryLock(ProceedingJoinPoint point) throws Throwable {

        logger.info("[RedisLockAspect.aroundTryLock] in");

        RedisTryLock redisLock = getLockAnnotation(point, RedisTryLock.class);
        String key = redisLock.value();
        int waitTime = redisLock.waitTime();
        int leaseTime = redisLock.leaseTime();
        boolean fair = redisLock.fair();

        Object result = null;
        try {
            if (redissonLock.tryLock(key, waitTime, leaseTime, fair)) {
                result = point.proceed();
            } else {
                logger.warn("[RedisLockAspect.aroundTryLock] key={} is occupied by another thread!", key);
            }
        } catch (Throwable throwable) {
            logger.error("[RedisLockAspect.aroundTryLock] error occurred, key={}", key, throwable);
            throw throwable;
        } finally {
            redissonLock.unlock(key, fair);
            logger.info("[RedisLockAspect.aroundTryLock] out");
        }
        return result;
    }

    private <T extends Annotation> T getLockAnnotation(ProceedingJoinPoint point, Class<T> clazz) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(clazz);
    }
}
