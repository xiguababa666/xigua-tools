package org.xyx.rest.retrofit;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * request body => JSON
 *
 * @author xueyongxin
 * @date 2019/4/2 21:44
 */

@Slf4j
public class RequestConverter<T> implements Converter<T, RequestBody> {

    private boolean debug;

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    RequestConverter(boolean debug) {
        this.debug = debug;
    }

    @Override
    public RequestBody convert(T t) {
        if (debug && t != null ) {
            log.info("REQUEST => {}", JSON.toJSONString(t));
        }
        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(t));
    }
}
