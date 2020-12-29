package org.xyx.rest.feign.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * yml
 * -
 * remote:
 *   servers:
 *     yourServerName:
 *       domain: yourDomain
 *       appKey: yourAppKey
 *       appSecret: yourSecret
 *       connectTimeout: 30
 *       writeTimeout: 30
 *       readTimeout: 30
 *
 * properties
 * -
 * remote.servers.yourServerName.domain=yourDomain
 * remote.servers.yourServerName.appKey=yourAppKey
 * remote.servers.yourServerName.appSecret=yourSecret
 *
 * @author xueyongxin
 * @date 2020/12/29
 */

@Component
@ConfigurationProperties("remote")
public class RemoteServerProperties {

    private Map<String, RemoteServerConfig> servers;

    public RemoteServerConfig get(String key) {
        return servers.get(key);
    }

}
