package org.xyx.redis.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xyx.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 分布式锁 切面
 *
 * @author xueyongxin
 */

@Aspect
public class LockAspect {

    private static final Logger logger = LoggerFactory.getLogger(LockAspect.class);

    @Around("@annotation(org.xyx.redis.lock.TryLock)")
    public Object aroundTryLock(ProceedingJoinPoint point) throws Throwable {

        Method method = getMethod(point);
        TryLock tryLock = method.getAnnotation(TryLock.class);
        String key = tryLock.key();
        int waitTime = tryLock.waitTime();
        int holdTime = tryLock.holdTime();
        TimeUnit unit = tryLock.timeUnit();
        String[] fields = tryLock.fields();
        LockType lockType = tryLock.type();

        Object[] params = getTryLockParams(point, method);
        Object[] keyParams = getLockKeyValues(params, fields);
        String lockKey = generateLockKey(key, keyParams);

        Object result;
        // 是否持有锁
        boolean holdLock = false;
        try {
            logger.info("[@TryLock] key={} waitTime={} holdTime={}", lockKey, waitTime, holdTime);
            // 尝试获取锁
            if (lockType.getLocker().tryLock(lockKey, unit, waitTime, holdTime)) {
                holdLock = true;
                result = point.proceed();
            } else {
                throw new DistributedLockerException(String.format("tryLock failed, key = %s", lockKey));
            }
        } finally {
            if (holdLock) {
                lockType.getLocker().unlock(lockKey);
            }
        }
        return result;
    }

    private Method getMethod(ProceedingJoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        return methodSignature.getMethod();
    }

    /**
     * 取key的唯一标识
     *
     * @param specifiedParams 指定入参
     * @param fields          指定成员变量名
     * @return 排好序的参数值
     */
    private Object[] getLockKeyValues(Object[] specifiedParams, String[] fields) {

        if (specifiedParams == null || specifiedParams.length == 0) {
            // 未指定入参
            return new Object[0];
        }

        if (fields.length == 0) {
            // 未指定成员变量，直接使用入参值
            return specifiedParams;
        }

        if (specifiedParams.length > 1) {
            // 指定成员变量，但指定了多个入参，不知道取那个入参的成员变量
            throw new DistributedLockerException(String.format("@TryLock: too many specified params to chose! params size:%s fields:%s",
                    specifiedParams.length, Arrays.toString(fields)));
        }

        return getLockKeyValueFromMembers(specifiedParams[0], fields);

    }

    private Object[] getLockKeyValueFromMembers(Object baseParam, String[] members) {

        Object[] ret = new Object[members.length];
        Class<?> c = baseParam.getClass();
        String curMember = "";
        try {
            for (int i = 0; i < members.length; i++) {
                // 反射取field值
                curMember = members[i];
                Field field = c.getDeclaredField(curMember);
                field.setAccessible(true);
                ret[i] = field.get(baseParam);
            }
        } catch (NoSuchFieldException e) {
            throw new DistributedLockerException(
                    String.format("@TryLock: parameter [%s] no such field [%s], check your *fields*", baseParam.getClass(), curMember));
        } catch (IllegalAccessException ex) {
            throw new DistributedLockerException(ex);
        }
        return ret;
    }

    private Object[] getTryLockParams(ProceedingJoinPoint point, Method method) {
        Parameter[] ps = method.getParameters();
        Object[] args = point.getArgs();
        List<Object> params = new LinkedList<>();
        for (int i = 0; i < ps.length; i++) {
            TryLockParam tryLockParam = ps[i].getAnnotation(TryLockParam.class);
            if (tryLockParam != null) {
                params.add(args[i]);
            }
        }
        return params.toArray();

    }


    private void checkKey(String key, Object... params) {
        int paramCount = params.length;
        int keyParamCount = StringUtils.subStrCount(key, "%s");
        if (paramCount != keyParamCount) {
            throw new DistributedLockerException(String.format("lock key format not match! key:%s, params:%s",
                    key, Arrays.toString(params)));
        }
    }

    private String generateLockKey(String key, Object... params) {
        checkKey(key, params);
        return String.format("xyx:lock:" + key, params);
    }


}
