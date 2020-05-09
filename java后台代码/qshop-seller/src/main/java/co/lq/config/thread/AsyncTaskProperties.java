package co.lq.config.thread;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 线程池配置属性类
 *
 * @author billy
 * @date 2019年10月31日14:58:18
 */
@Data
@Component
@ConfigurationProperties(prefix = "task.pool")
public class AsyncTaskProperties {

    private int corePoolSize;

    private int maxPoolSize;

    private int keepAliveSeconds;

    private int queueCapacity;
}
