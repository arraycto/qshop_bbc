package co.lq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import cn.hutool.core.util.StrUtil;
import co.lq.listener.RedisKeyExpirationListener;
import co.lq.modules.order.service.StoreOrderService;
import lombok.AllArgsConstructor;

/**
 * redis监听配置
 *
 * @author billy
 * @since 2020-02-27
 */

@Configuration
@AllArgsConstructor
public class RedisListenerConfig {

    private final RedisTemplate<String, String> redisTemplate;
    private final RedisConfigProperties         redisConfigProperties;
    private final StoreOrderService             storeOrderService;

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory factory) {
        String topic = StrUtil.format("__keyevent@{}__:expired", redisConfigProperties.getDatabase());
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(
                new RedisKeyExpirationListener(redisTemplate, redisConfigProperties, storeOrderService),
                new PatternTopic(topic));
        return container;
    }
}
