package org.xyx.redis.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xyx.redis.ReflectUtil;
import org.xyx.utils.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


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
        if (isSingleKey(params)) {

        } else {

        }


        Object cached = type.getCacher().get(key);
        if (cached != null) {
            logger.info("[CacheAspect] cached! key:{}", key);
            return cached;
        }


        logger.info("[CacheAspect] not cached! key:{}", key);
        Object notCached = point.proceed();
        type.getCacher().set(key, notCached);

        return notCached;

    }

    private List<String> getKeys(String key, Object... params) {

        List<String> keys = new LinkedList<>();



        return keys;

    }


    private boolean isSingleKey(Object... params) {
        if (params.length == 0) {
            return true;
        }

        int collectionCount = 0;
        for (Object p : params) {
            if (p instanceof Collection) collectionCount++;
        }
        if ( collectionCount > 1) {
            throw new CacheDataException(String.format("cache key params not supported: %s collection", collectionCount));
        }

        return collectionCount == 0;
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
