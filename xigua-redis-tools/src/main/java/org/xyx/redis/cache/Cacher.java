package org.xyx.redis.cache;

import com.fasterxml.jackson.databind.JavaType;

import java.util.List;
import java.util.Map;

/**
 * description here
 *
 * @author xueyongxin
 */
public interface Cacher {

    /**
     * @param key
     * @return 命中缓存的数据
     * */
    Object get(String key, Class<?> clazz);

    /**
     * @param key
     * @return 命中缓存的数据
     * */
    Object get(String key, JavaType javaType);

    /**
     * @param keys
     * @return 命中缓存的数据
     * */
    Map<String, Object> get(List<String> keys);

    /**
     * @param key
     * @param v
     * */
    void set(String key, Object v);

    /**
     * @param key
     * @param v
     * @param time 过期时间，单位是秒
     * */
    void set(String key, Object v, long time);


}
