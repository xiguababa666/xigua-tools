package org.xyx.redis.cache;

import com.fasterxml.jackson.databind.JavaType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.xyx.redis.ReflectUtil;
import org.xyx.redis.cache.anno.CacheMultiKeys;
import org.xyx.redis.cache.anno.CacheSingleKey;
import org.xyx.utils.JsonUtil;

import java.lang.reflect.Method;
import java.util.*;


/**
 *
 * @author xueyongxin
 */

@Aspect
public class CacheAspect {


    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    @Value("${spring.application.name:cache}")
    private String appName;


    @Around("@annotation(cacheSingleKey)")
    public Object aroundCacheSingleKey(ProceedingJoinPoint point, CacheSingleKey cacheSingleKey) throws Throwable {

        Object cached;
        Object[] params = point.getArgs();

        Method method = ReflectUtil.getMethod(point);
        String cacheKey = generateCacheKey(method.getName(), params);

        Class<?> returnType = method.getReturnType();
        CacheType type = cacheSingleKey.type();
        if (Collection.class.isAssignableFrom(returnType)) {
            Class<?> elementType = cacheSingleKey.elementType();
            JavaType javaType = JsonUtil.getJavaType((Class<? extends Collection>) returnType, elementType);
            cached = type.getCacher().get(cacheKey, javaType);
        } else {
            cached = type.getCacher().get(cacheKey, returnType);
        }

        if (cached == null) {
            logger.info("[CacheAspect] not cached, cacheKey = {}", cacheKey);
            cached = point.proceed();
            type.getCacher().set(cacheKey, cached);
        } else {
            logger.info("[CacheAspect] cached, cacheKey = {}, value = {}", cacheKey, cached);
        }
        return cached;

    }

    @Around("@annotation(cacheMultiKeys)")
    public Object aroundCacheMultiKeys(ProceedingJoinPoint point, CacheMultiKeys cacheMultiKeys) throws Throwable {

        Method method = ReflectUtil.getMethod(point);
        CacheType type = cacheMultiKeys.type();
        Class<?> result = cacheMultiKeys.elementType();

        Object[] params = point.getArgs();

        int collectionIndex = getCollectionIndex(params);
        Map<String, Object> keyMap = generateKeys(method.getName(), collectionIndex, params);
        List<String> cacheKeys = new LinkedList<>(keyMap.keySet());
        Map<String, Object> cached = type.getCacher().get(cacheKeys);
        logger.info("[CacheAspect] cacheKeys = {}", cacheKeys);

        return point.proceed();
    }


    private String generateCacheKey(String methodName, Object... params) {
        StringBuilder sb = new StringBuilder(appName);
        sb.append("_").append(methodName);
        for (Object p : params) {
            sb.append("_").append(p);
        }
        return sb.toString();
    }



    private Map<String, Object> generateKeys(String methodName, int collectionIndex, Object... params) {

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
            keys.put(generateCacheKey(methodName, p), id);
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


}
