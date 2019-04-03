package org.xyx.rest.retrofit;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * description
 *
 * @author xueyongxin
 * @date 2019/4/2 21:44
 */

@Component
public class ConvertFactory extends Converter.Factory {

    @Value("${retrofit.debug:false}")
    private boolean debug;

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new ResponseConverter<>(type, debug);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations,
                                                          Retrofit retrofit) {
        return new RequestConverter<>(debug);
    }

}
