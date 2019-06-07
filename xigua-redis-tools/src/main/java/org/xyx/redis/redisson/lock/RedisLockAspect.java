package org.xyx.redis.redisson.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

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
    private RedissonClient redissonClient;

    /**
     *
     * lock
     *
     * */
    @Around("@annotation(RedisLock)")
    public Object aroundLock(ProceedingJoinPoint point) throws Throwable {

        logger.info("[RedisLockAspect.aroundLock] Begin");

        RedisLock redisLock = getLockAnnotation(point, RedisLock.class);
        String key = redisLock.value();
        int leaseTime = redisLock.leaseTime();
        boolean fair = redisLock.fair();

        Object result;
        RLock lock = null;
        try {
            /*
             *
             * 获取锁，阻塞式，获取后需要主动释放
             *
             * key 锁key
             * leaseTime 持有时间，持有锁超过此时间(单位：秒)，其他线程可以获取锁
             * fair 是否公平锁
             *
             */
            lock = getLock(key, fair);
            if (leaseTime == 0) {
                lock.lock();
            } else {
                lock.lock(leaseTime, TimeUnit.SECONDS);
            }
            result = point.proceed();

        } catch (Throwable throwable) {
            logger.error("[RedisLockAspect.aroundLock] error occurred, key={}", key, throwable);
            throw throwable;
        } finally {
            if (lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
            logger.info("[RedisLockAspect.aroundLock] End");
        }
        return result;
    }

    /**
     *
     * tryLock
     *
     * */
    @Around("@annotation(RedisTryLock)")
    public Object aroundTryLock(ProceedingJoinPoint point) throws Throwable {

        logger.info("[RedisLockAspect.aroundTryLock] Begin");

        RedisTryLock redisLock = getLockAnnotation(point, RedisTryLock.class);
        String key = redisLock.value();
        int waitTime = redisLock.waitTime();
        int leaseTime = redisLock.leaseTime();
        boolean fair = redisLock.fair();

        Object result = null;
        RLock lock = null;
        try {

            /**
             *
             * 尝试获取锁
             *
             * key 锁key
             * waitTime 等待时间，超过此时间，直接返回获取锁失败(单位：秒)
             * leaseTime 持有时间，持有锁超过此时间，其他线程可以获取锁
             * fair 是否公平锁
             *
             * */
            lock = getLock(key, fair);
            if (lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)) {
                result = point.proceed();
            } else {
                logger.warn("[RedisLockAspect.aroundTryLock] key={} is occupied by another thread!", key);
            }
        } catch (Throwable throwable) {
            logger.error("[RedisLockAspect.aroundTryLock] error occurred, key={}", key, throwable);
            throw throwable;
        } finally {
            if (lock != null) {
                lock.unlock();
            }
            logger.info("[RedisLockAspect.aroundTryLock] End");
        }
        return result;
    }

    private <T extends Annotation> T getLockAnnotation(ProceedingJoinPoint point, Class<T> clazz) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(clazz);
    }

    private RLock getLock(String key, boolean fair) {
        RLock lock;
        if (fair) {
            lock = redissonClient.getFairLock(key);
        } else {
            lock = redissonClient.getLock(key);
        }
        return lock;
    }
}
