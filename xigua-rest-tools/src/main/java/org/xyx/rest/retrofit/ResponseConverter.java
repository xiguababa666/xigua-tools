package org.xyx.rest.retrofit;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 返回值转换成对象
 *
 * @author xueyongxin
 * @date 2019/4/2 21:44
 */

@Slf4j
public class ResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Type type;

    private boolean debug;

    ResponseConverter(Type type, boolean debug) {
        this.type = type;
        this.debug = debug;
    }

    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String str = responseBody.string();
        // 此处可以添加log
        if (debug) {
            log.info("RESPONSE => {}", str);
        }
        return JSON.parseObject(str, type);
    }

}
