package org.xyx.rest.feign;

import feign.Feign;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.xyx.rest.feign.anno.RestfulApi;
import org.xyx.rest.feign.anno.RestfulService;
import org.xyx.rest.feign.interceptor.HeaderInterceptor;
import org.xyx.rest.feign.interceptor.SignInterceptor;
import org.xyx.rest.feign.properties.RemoteServerConfig;
import org.xyx.rest.feign.properties.RemoteServerProperties;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * RestfulService注入，此处实现依赖【Spring、java反射】
 *
 * @author xueyongxin
 * @date 2020/12/29
 */

@Component
public class ApiInject extends InstantiationAwareBeanPostProcessorAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Resource
    private RemoteServerProperties properties;


    /**
     * 复用
     * */
    private final static Map<String, Object> restServices = new HashMap<>(4);

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) {

        ReflectionUtils.doWithFields(bean.getClass(), field -> {
            RestfulApi restApi = field.getAnnotation(RestfulApi.class);
            if (restApi == null) {
                return;
            }

            Class<?> clazz = field.getType();
            RestfulService restService = clazz.getAnnotation(RestfulService.class);
            if (restService == null) {
                return;
            }

            String server = restService.server();
            RemoteServerConfig config = properties.get(server);
            if (config == null) {
                throw new IllegalArgumentException(String.format("[feign] No config found for server[%s]", server));
            }

            String host = config.getHost();
            if (StringUtils.isEmpty(host)) {
                throw new IllegalArgumentException(String.format("[feign] No host found for server[%s]", server));
            }

            Class<? extends RequestInterceptor>[] interceptors = restService.interceptor();

            Object object;
            if (!restServices.containsKey(host)) {
                object = createObject(config, clazz, interceptors);
                restServices.put(host, object);
            } else {
                object = restServices.get(host);
            }

            field.setAccessible(true);
            field.set(bean, object);

        });

        return super.postProcessAfterInstantiation(bean, beanName);
    }

    private Object createObject(RemoteServerConfig config, Class<?> clazz, Class<? extends RequestInterceptor>[] interceptors) {

        String host = config.getHost();
        int connectionTimeout = config.getConnectTimeout() * 1000;
        int readTimeout = config.getReadTimeout() * 1000;
        int retryPeriod = config.getRetryPeriod();
        int retries = config.getRetries();

        Feign.Builder builder = Feign.builder()
                .encoder(new RequestEncoder())
                .decoder(new ResponseDecoder())
                .options(new Request.Options(connectionTimeout, readTimeout))
                .retryer(new Retryer.Default(retryPeriod, retryPeriod, retries));


        List<RequestInterceptor> allInterceptors = new LinkedList<>();
        allInterceptors.add(new HeaderInterceptor());

        String appKey = config.getAppKey();
        if (appKey != null && appKey.length() > 0) {
            allInterceptors.add(new SignInterceptor(config.getAppKey(), config.getAppSecret()));
        }

        if (interceptors != null && interceptors.length > 0) {
            for (Class<? extends RequestInterceptor> clz : interceptors) {
                if (RequestInterceptor.class.isAssignableFrom(clz)) {
                    allInterceptors.add(applicationContext.getBean(clz));
                }
            }

        }
        builder.requestInterceptors(allInterceptors);

        return builder.target(clazz, host);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
