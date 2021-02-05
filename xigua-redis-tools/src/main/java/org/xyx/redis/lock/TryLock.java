package org.xyx.redis.lock;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * tryLock注解
 *
 * 失败会抛异常 {@link DistributedLockerException}
 *
 * @author xueyongxin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TryLock {

    /**
     * 默认为methodName
     * */
    String name() default "";

    /**
     * SPEL表达式
     *
     * @TryLock(key = "xyx", keys = {"#a"})
     * public void testLockKey(Integer a, String b) {}
     *
     * @TryLock(key = "xyx", keys = {"#param.id", "#param.name"})
     * public void testLockKey(LockParam param) {}
     *
     * */
    String[] keys() default {};

    /**
     * 默认redis分布式锁
     *
     * */
    LockType type() default LockType.REDISSON;

    /**
     * 最大等待时间
     */
    int waitTime() default 0;

    /**
     * 最大持有时间
     */
    int holdTime() default 5;

    /**
     * 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
