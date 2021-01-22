package org.xyx.redis.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xyx.redis.ReflectUtil;


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




}
