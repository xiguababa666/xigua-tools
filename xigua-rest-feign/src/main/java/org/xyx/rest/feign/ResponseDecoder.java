package org.xyx.rest.feign;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * description here
 *
 * @author xueyongxin002
 * @date 2020/12/29
 */
public class ResponseDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {

        if (response.status() != 200) {
            // todo
        }

        Response.Body body = response.body();
        String responseBody = Util.toString(body.asReader());

        // todo json

        return null;
    }
}
