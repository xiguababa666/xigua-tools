package org.xyx.rest.retrofit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * description
 *
 * @author xyx
 * @date 2018/9/14 13:32
 */
public class ResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Type type;

    ResponseConverter(Type type) {
        this.type = type;

    }

    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        BufferedSource source = Okio.buffer(responseBody.source());
        String str = source.readUtf8();
        try {
            return JSON.parseObject(str, type);
        } catch (JSONException e) {
            return null;
        }
    }

}
