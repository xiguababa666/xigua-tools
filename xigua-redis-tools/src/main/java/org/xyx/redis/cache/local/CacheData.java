package org.xyx.redis.cache.local;

/**
 * description here
 *
 * @author xueyongxin
 */
public class CacheData {

    private final long time;

    private final Object data;

    public CacheData(long time, Object data) {
        this.time = time;
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public Object getData() {
        return data;
    }
}
