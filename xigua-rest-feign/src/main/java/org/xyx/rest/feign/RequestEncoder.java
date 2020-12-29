package org.xyx.rest.feign;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;

import java.lang.reflect.Type;

/**
 * description here
 *
 * @author xueyongxin002
 * @date 2020/12/29
 */
public class RequestEncoder implements Encoder {

    @Override
    public void encode(Object o, Type type, RequestTemplate request) throws EncodeException {

        // todo
    }
}
