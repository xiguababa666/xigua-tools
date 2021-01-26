package org.xyx.redis.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xyx.redis.ReflectUtil;
import org.xyx.utils.StringUtils;

import java.util.*;


/**
 *
 * @author xueyongxin
 */

@Aspect
public class CacheAspect {


    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);


    @Pointcut("@annotation(org.xyx.redis.cache.CacheData)")
    public void aroundDataCache() {
    }

    @Around("aroundDataCache()")
    public Object aroundDataCache(ProceedingJoinPoint point) throws Throwable {

        CacheData dataCache = ReflectUtil.getAnnotation(point, CacheData.class);

        String key = dataCache.key();
        CacheType type = dataCache.type();

        Object[] params = point.getArgs();

        checkKey(key, params);
        int collectionIndex = getCollectionIndex(params);
        List<String> cacheKeys;
        if (-1 == collectionIndex) {
            cacheKeys = new ArrayList<>();
            cacheKeys.add(generateSingleKey(key, params));
        } else {
            cacheKeys = generateKeys(key, collectionIndex, params);
        }
        logger.info("[CacheAspect] cacheKeys = {}", cacheKeys);

        return point.proceed();

//        Object cached = type.getCacher().get(key);
//        if (cached != null) {
//            logger.info("[CacheAspect] cached! key:{}", key);
//            return cached;
//        }
//
//
//        logger.info("[CacheAspect] not cached! key:{}", key);
//        Object notCached = point.proceed();
//        type.getCacher().set(key, notCached);
//
//        return notCached;

    }


    private String generateSingleKey(String key, Object... params) {
        return String.format(key, params);
    }



    private List<String> generateKeys(String key, int collectionIndex, Object... params) {

        List<String> keys = new LinkedList<>();
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
            keys.add(String.format(key, p));
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
