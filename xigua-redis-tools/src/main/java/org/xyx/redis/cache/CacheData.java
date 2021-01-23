package org.xyx.redis.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author xueyongxin
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheData {

    String key();

    /**
     * "redis cache" OR "local cache"
     * */
    CacheType type() default CacheType.REDIS;

    /**
     * 过期时间，单位：秒
     *
     * */
    long expire() default -1;

}