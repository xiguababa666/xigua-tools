package org.xyx.redis.cache.anno;

import org.xyx.redis.cache.CacheType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 缓存更新时delete（暂时先删除）
 *
 * @author xueyongxin
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheUpdate {

    /**
     * 缓存名
     * */
    String name() default "";

    /**
     * 与name共同确定一个缓存
     * 如：name_key1_key2_key3
     * */
    String[] keys() default {};

    /**
     * "redis cache" OR "local cache"
     * */
    CacheType type() default CacheType.REDIS;

}
