package org.xyx.redis.cache.anno;

import org.xyx.redis.cache.CacheType;

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
public @interface CacheMultiKeys {

    String name() default "";

    String[] keys() default {};

    /**
     * "redis cache" OR "local cache"
     * */
    CacheType type() default CacheType.REDIS;

    /**
     * 过期时间，单位：秒
     *
     * */
    long expire() default -1;

    /**
     * 返回集合元素的类型
     * */
    Class<?> elementType() default Object.class;

}
