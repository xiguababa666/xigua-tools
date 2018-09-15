package org.xyx.rest.retrofit;

import okhttp3.OkHttpClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * RestService注入，此处实现依赖Spring
 *
 * @author xyx
 * @date 2018/9/14 15:35
 */

@Component
public class RestApiAutoWired extends InstantiationAwareBeanPostProcessorAdapter {

    /**
     * 复用
     * */
    private static Map<String, Object> restServices = new HashMap<>(4);

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {

        ReflectionUtils.doWithFields(bean.getClass(), (field) -> {
            RestApi restApi = field.getAnnotation(RestApi.class);
            if (restApi == null) {
                return;
            }

            Class<?> clazz = field.getType();
            RestService restService = clazz.getAnnotation(RestService.class);

            if (restService == null) {
                return;
            }
            String baseUrl = restService.value();
            if (StringUtils.isEmpty(baseUrl)) {
                return;
            }

            Object object = null;
            if (!restServices.containsKey(baseUrl)) {
                object = createObject(baseUrl, clazz);
                restServices.put(baseUrl, object);
            }

            // 随着java版本的迭代，此处以后会不会不支持？
            field.setAccessible(true);
            field.set(bean, object);

        });

        return super.postProcessAfterInstantiation(bean, beanName);
    }

    private Object createObject(String baseUrl, Class<?> clazz) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new RestLogInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(clientBuilder.build())
                .addConverterFactory(new ConvertFactory())
                .build();

        return retrofit.create(clazz);
    }
}
