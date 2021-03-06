package org.xyx.redis.cache.local;

import com.fasterxml.jackson.databind.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.xyx.redis.cache.BaseCacher;
import org.xyx.utils.ThreadPool;

import javax.annotation.PreDestroy;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * description here
 *
 * @author xueyongxin
 */

@Lazy
@Component
public class LocalCacher extends BaseCacher implements InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(LocalCacher.class);

    private final Map<String, LocalData> cache = new ConcurrentHashMap<>();

    private ScheduledExecutorService executor;

    @Override
    public void afterPropertiesSet() {
        // 启动过期检查
        executor = ThreadPool.getSingleScheduledThreadPool();
        executor.scheduleWithFixedDelay(this::checkExpired, 30, 30, TimeUnit.MINUTES);
    }


    @PreDestroy
    public void destroy() {
        executor.shutdown();
    }


    private void checkExpired() {
        try {
            for (String key : cache.keySet()) {
                if (isExpired(key)) {
                    cache.remove(key);
                    logger.info("[LocalCacher] expired key: {}", key);
                }
            }
        } catch (Exception e) {
            logger.warn("[LocalCacher] error!", e);
        }
    }


    private LocalData wrapCacheData(Object v, long time) {
        return new LocalData(System.currentTimeMillis() + generateTimeOffset(time), v);
    }

    private boolean isExpired(String key) {
        LocalData localData = cache.get(key);
        if (localData == null) {
            return true;
        }
        long expire = localData.getTime();
        return expire - System.currentTimeMillis() <= 0;
    }

    private Object getIfNotExpired(String key) {
        if (isExpired(key)) {
            cache.remove(key);
            return null;
        }
        LocalData data = cache.get(key);
        return data != null ? data.getData() : null;
    }

    public Object get(String key) {
        return getIfNotExpired(key);
    }

    @Override
    public Object get(String key, Class<?> clazz) {
        return get(key);
    }

    @Override
    public Object get(String key, JavaType javaType) {
        return get(key);
    }

    @Override
    public Map<String, Object> get(List<String> keys) {
        Map<String, Object> values = new LinkedHashMap<>();
        for (String key : keys) {
            Object v = getIfNotExpired(key);
            if (v != null) {
                values.put(key, v);
            }
        }
        return values;
    }

    @Override
    public void set(String key, Object v) {
        long DAY_MS = 24 * 60 * 60 * 1000;
        set(key, v, DAY_MS);
    }

    @Override
    public void set(String key, Object v, long time) {
        cache.put(key, wrapCacheData(v, time));
    }



}
