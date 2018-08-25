package org.xyx.redis.utils;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

    String value() default "";

    int leaseTime() default 0;

    boolean fair() default false;
}
