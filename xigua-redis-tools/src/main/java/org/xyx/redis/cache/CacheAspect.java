package org.xyx.redis.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xyx.redis.ReflectUtil;
import org.xyx.redis.cache.anno.CacheMultiKeys;
import org.xyx.redis.cache.anno.CacheSingleKey;
import org.xyx.utils.StringUtils;

import java.util.*;


/**
 *
 * @author xueyongxin
 */

@Aspect
public class CacheAspect {


    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);


    @Pointcut("@annotation(org.xyx.redis.cache.anno.CacheSingleKey)")
    public void aroundCacheSingleKey() {
    }

    @Pointcut("@annotation(org.xyx.redis.cache.anno.CacheMultiKeys)")
    public void aroundCacheMultiKeys() {
    }

    @Around("aroundCacheSingleKey()")
    public Object aroundCacheSingleKey(ProceedingJoinPoint point) throws Throwable {

        CacheSingleKey dataCache = ReflectUtil.getAnnotation(point, CacheSingleKey.class);

        String key = dataCache.key();
        CacheType type = dataCache.type();

        // todo Collection 怎么处理？
        Class<?> result = dataCache.result();

        Object[] params = point.getArgs();

        checkKey(key, params);
        String cacheKey = generateSingleKey(key, params);
        Object cached = type.getCacher().get(cacheKey, result);
        if (cached == null) {
            logger.info("[CacheAspect] not cached, cacheKey = {}", cacheKey);
            cached = point.proceed();
            type.getCacher().set(cacheKey, cached);
        } else {
            logger.info("[CacheAspect] cached, cacheKey = {}, value = {}", cacheKey, cached);
        }
        return cached;

    }

    /**
     * todo 多key时，已命中和未命中的分别如何处理
     * */
    @Around("aroundCacheMultiKeys()")
    public Object aroundCacheMultiKeys(ProceedingJoinPoint point) throws Throwable {

        CacheMultiKeys dataCache = ReflectUtil.getAnnotation(point, CacheMultiKeys.class);

        String key = dataCache.key();
        CacheType type = dataCache.type();
        Class<?> result = dataCache.result();

        Object[] params = point.getArgs();

        checkKey(key, params);
        int collectionIndex = getCollectionIndex(params);
        Map<String, Object> keyMap = generateKeys(key, collectionIndex, params);
        List<String> cacheKeys = new LinkedList<>(keyMap.keySet());
        Map<String, Object> cached = type.getCacher().get(cacheKeys);
        logger.info("[CacheAspect] cacheKeys = {}", cacheKeys);

        return point.proceed();
    }


    private String generateSingleKey(String key, Object... params) {
        return String.format(key, params);
    }



    private Map<String, Object> generateKeys(String key, int collectionIndex, Object... params) {

        Map<String, Object> keys = new LinkedHashMap<>();
        Collection<Object> ids = (Collection<Object>) params[collectionIndex];
        for (Object id : ids) {
            Object[] p = new Object[params.length];
            for (int i = 0; i < params.length; i++) {
                if (i == collectionIndex) {
                    p[i] = id;
                } else {
                    p[i] = params[i];
                }
            }
            keys.put(String.format(key, p), id);
        }
        return keys;

    }


    private int getCollectionIndex(Object... params) {
        if (params.length == 0) {
            return -1;
        }

        int collectionCount = 0;
        int collectionIndex = -1;
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof Collection) {
                collectionCount++;
                collectionIndex = i;
            }
        }
        if ( collectionCount > 1) {
            throw new CacheDataException(String.format("cache key params not supported: %s collection", collectionCount));
        }

        return collectionIndex;
    }


    private void checkKey(String key, Object... params) {
        int paramCount = params.length;
        int keyParamCount = StringUtils.subStrCount(key, "%s");
        if (paramCount != keyParamCount) {
            throw new CacheDataException(String.format("cache key format not match! key:%s, params:%s",
                    key, Arrays.toString(params)));
        }
    }

}
