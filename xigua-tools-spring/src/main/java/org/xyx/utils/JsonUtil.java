package org.xyx.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author xueyongxin
 * @date 2020-01-07
 * */
public class JsonUtil {

    /**
     * 简单判断是否是json字符串
     * */
    public static boolean mayBeJSON(Object object) {
        if (object == null || !String.class.isAssignableFrom(object.getClass())) {
            return false;
        }
        String str = (String) object;
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        return (str.startsWith("{") && str.endsWith("}"))
                || (str.startsWith("[") && str.endsWith("]"));
    }

    public static <T> T jsonStr2Obj(String json, Class<T> clazz) {
        try {
            return getInstance().readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String obj2JsonStr(Object obj) {
        if (obj == null){
            return StringUtils.EMPTY;
        }
        try {
            return getInstance().writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> T jsonStr2Obj(String json, JavaType clazz) {
        try {
            return getInstance().readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public static <T> T jsonStr2Obj(String json, TypeReference<T> typeReference) {
        try {
            return getInstance().readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> List<T> parseList(String str, Class<T> clazz) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        JavaType type = getInstance().getTypeFactory().constructParametricType(List.class, clazz);
        try {
            return getInstance().readValue(str, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    private static ObjectMapper getInstance() {
        return SingletonHolder.instance;
    }

    private static final class SingletonHolder {
        private static final ObjectMapper instance = new ObjectMapper();

        static {
            // 忽略JSON字符串中存在而Java对象实际没有的属性
            instance.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //模式时间格式
            instance.setDateFormat(new SimpleDateFormat(DateFormatUtil.DATE_TIME.getPattern()));
            //反序列化的作用是确定是否强制让非数组模式的json字符串与java集合类型相匹配
            instance.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        }
    }


}
