package org.xyx.redis.cache;

import java.util.Collection;
import java.util.List;

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
    Object get(String key);

    /**
     * @param keys
     * @return 命中缓存的数据
     * */
    List<Object> get(Collection<String> keys);

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
