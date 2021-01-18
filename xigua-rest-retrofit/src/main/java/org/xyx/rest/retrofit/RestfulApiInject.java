package org.xyx.rest.retrofit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import retrofit2.Retrofit;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * RestfulService注入，此处实现依赖【Spring、java的反射】
 *
 * @author xueyongxin
 * @date 2019/4/2 21:44
 */

@Component
public class RestfulApiInject extends InstantiationAwareBeanPostProcessorAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Resource
    private RetrofitProperties properties;

    @Resource
    private ConvertFactory convertFactory;

    /**
     * 复用
     * */
    private static Map<String, Object> restServices = new HashMap<>(4);

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

            String key = restService.key();
            RetrofitRestfulConfig config = properties.get(key);
            if (config == null) {
                throw new IllegalArgumentException(String.format("No config found for key[%s]", key));
            }

            String domain = config.getDomain();
            if (StringUtils.isEmpty(domain)) {
                throw new IllegalArgumentException(String.format("No domain found for key[%s]", key));
            }

            Class<? extends Interceptor>[] interceptors = restService.interceptor();

            Object object;
            if (!restServices.containsKey(domain)) {
                object = createObject(config, clazz, interceptors);
                restServices.put(domain, object);
            } else {
                object = restServices.get(domain);
            }

            field.setAccessible(true);
            field.set(bean, object);

        });

        return super.postProcessAfterInstantiation(bean, beanName);
    }

    private Object createObject(RetrofitRestfulConfig config, Class<?> clazz, Class<? extends Interceptor>[] interceptors) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder()
                .connectTimeout(config.getConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(config.getWriteTimeout(), TimeUnit.SECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.SECONDS);

        if (interceptors != null && interceptors.length > 0) {
            for (Class clz : interceptors) {
                if (Interceptor.class.isAssignableFrom(clz)) {
                    clientBuilder.addInterceptor((Interceptor) applicationContext.getBean(clz));
                }
            }

        }

        if (!StringUtils.isEmpty(config.getAppKey())) {
            //clientBuilder.addInterceptor(new SignInterceptor(config.getAppKey(), config.getAppSecret()));
        }

        clientBuilder.addInterceptor(new RequestInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getDomain())
                .client(clientBuilder.build())
                .addConverterFactory(convertFactory)
                .build();

        return retrofit.create(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
