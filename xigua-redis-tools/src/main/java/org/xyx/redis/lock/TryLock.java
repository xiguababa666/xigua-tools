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
     * key
     * 满足如下规则就行： abc  或  abc:%s:%s  或  abc_%s_%s  或  abc-%s-%s
     */
    String key();

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


    /**
     * 指定成员变量作唯一标识
     * 【注】指定此项时，认为是在params指定的对象中取成员变量作，params项只能指定一个
     * 例：
     *
     * @TryLock(key = "abc:%s", params = {"param3"}, members = {"orderId"})
     * public void test(String param1, String param2, OrderInfo param3) {
     * }
     * <p>
     * class OrderInfo {
     * private String orderId;
     * ......
     * }
     */
    String[] fields() default {};

}
