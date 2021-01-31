package org.xyx.redis;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

/**
 * description here
 *
 * @author xueyongxin002
 * @date 2021/1/22 下午4:11
 */
public class ReflectUtil {


    public static Method getMethod(ProceedingJoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        return methodSignature.getMethod();
    }



    public static  <T extends Annotation> T getAnnotation(ProceedingJoinPoint point, Class<T> clazz) {
        Method method = getMethod(point);
        return method.getAnnotation(clazz);
    }



    public static  <T extends Annotation> Object[] getAnnotationParams(ProceedingJoinPoint point, Class<T> annotation) {
        Method method = getMethod(point);
        Parameter[] ps = method.getParameters();
        Object[] args = point.getArgs();
        List<Object> params = new LinkedList<>();
        for (int i = 0; i < ps.length; i++) {
            T tryLockParam = ps[i].getAnnotation(annotation);
            if (tryLockParam != null) {
                params.add(args[i]);
            }
        }
        return params.toArray();
    }

}
