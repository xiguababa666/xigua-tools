package org.xyx.redis;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

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

    private static final LocalVariableTableParameterNameDiscoverer discoverer;

    static {
        discoverer = new LocalVariableTableParameterNameDiscoverer();
    }

    public static Method getMethod(ProceedingJoinPoint point) throws NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        return point.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes());
    }

    /**
     *
     * 方法的参数名和参数值绑定
     *
     * */
    public static EvaluationContext bindParam(Method method, Object[] args) {
        //获取方法的参数名
        String[] params = discoverer.getParameterNames(method);

        int paramSize = params == null ? 0 : params.length;

        //将参数名与参数值对应起来
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramSize; i++) {
            context.setVariable(params[i], args[i]);
        }
        return context;
    }


    public static <T extends Annotation> T getAnnotation(ProceedingJoinPoint point, Class<T> clazz) throws NoSuchMethodException {
        Method method = getMethod(point);
        return method.getAnnotation(clazz);
    }


    public static <T extends Annotation> Object[] getAnnotationParams(ProceedingJoinPoint point, Class<T> annotation) throws NoSuchMethodException {
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
