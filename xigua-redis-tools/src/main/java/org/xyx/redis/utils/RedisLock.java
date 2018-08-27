package org.xyx.redis.utils;

import java.lang.annotation.*;

/**
 * description
 *
 * @author xyx
 * @date 2018/8/23 18:11
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

    String value() default "";

    int leaseTime() default 0;

    boolean fair() default false;
}
