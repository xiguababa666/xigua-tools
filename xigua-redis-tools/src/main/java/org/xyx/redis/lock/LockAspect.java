package org.xyx.redis.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.xyx.redis.ReflectUtil;
import org.xyx.utils.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
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

    @Value("${spring.application.name:}")
    private String appName;

    private final ExpressionParser parser = new SpelExpressionParser();

    @Around("@annotation(tryLock)")
    public Object aroundTryLock(ProceedingJoinPoint point, TryLock tryLock) throws Throwable {

        Method method = ReflectUtil.getMethod(point);
        String key = tryLock.name();
        if (StringUtils.isEmpty(key)) {
            key = method.getName();
        }
        String[] keys = tryLock.keys();
        Arrays.sort(keys);
        int waitTime = tryLock.waitTime();
        int holdTime = tryLock.holdTime();
        TimeUnit unit = tryLock.timeUnit();
        LockType lockType = tryLock.type();

        //获取方法的参数值
        Object[] args = point.getArgs();
        List<Object> keyParams = getSPELParams(method, args, keys);
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


    private List<Object> getSPELParams(Method method, Object[] args, String[] spel) {

        if (spel == null || spel.length == 0) {
            return Collections.emptyList();
        }

        //获取方法的参数值
        EvaluationContext context = ReflectUtil.bindParam(method, args);
        List<Object> keyParams = new LinkedList<>();

        //根据spel表达式获取值
        for (String r : spel) {
            keyParams.add(parser.parseExpression(r).getValue(context));
        }
        return keyParams;
    }


    private String generateLockKey(String key, List<Object> params) {
        StringBuilder sb = new StringBuilder(appName);
        sb.append("_LOCK_").append(key);
        for (Object o : params) {
            sb.append('_').append(o.toString());
        }
        return sb.toString();
    }


}
