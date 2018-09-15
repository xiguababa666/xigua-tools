package org.xyx.rest.retrofit;

import com.alibaba.fastjson.JSON;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

import java.io.IOException;

/**
 * description
 *
 * @author xyx
 * @date 2018/9/14 13:32
 */
public class RequestConverter<T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(T t) throws IOException {
        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(t));
    }
}
