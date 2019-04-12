package org.xyx.rest.retrofit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * description
 *
 * @author xueyongxin
 * @date 2019/4/2 21:44
 */

@ToString
@Getter
@Setter
@Component
@ConfigurationProperties("retrofit")
public class RetrofitProperties {

    private Map<String, RetrofitRestfulConfig> config;

    RetrofitRestfulConfig get(String key) {
        return config.get(key);
    }

}
