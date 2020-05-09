package co.lq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * reids相关配置
 *
 * @author billy
 * @since 2020-02-27
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfigProperties {

    private String host     = "host";
    private String port     = "port";
    private String password = "password";
    private String database = "database";

}
