package org.xyx.redis.utils;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisTryLock {

    String value() default "";

    int waitTime() default 0;

    int leaseTime() default 0;

    boolean fair() default false;
}
