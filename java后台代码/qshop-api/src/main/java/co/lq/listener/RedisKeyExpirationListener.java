package co.lq.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.util.StrUtil;
import co.lq.config.RedisConfigProperties;
import co.lq.constant.ShopConstants;
import co.lq.modules.order.entity.StoreOrder;
import co.lq.modules.order.service.StoreOrderService;
import lombok.extern.slf4j.Slf4j;

/**
 * redis过期监听
 *
 * @author billy
 * @since 2020-02-27
 */
@Component
@Slf4j
public class RedisKeyExpirationListener implements MessageListener {

    private RedisTemplate<String, String> redisTemplate;
    private RedisConfigProperties         redisConfigProperties;
    private StoreOrderService             storeOrderService;

    public RedisKeyExpirationListener(RedisTemplate<String, String> redisTemplate,
                                      RedisConfigProperties redisConfigProperties,
                                      StoreOrderService storeOrderService) {
        this.redisTemplate = redisTemplate;
        this.redisConfigProperties = redisConfigProperties;
        this.storeOrderService = storeOrderService;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        RedisSerializer<?> serializer = redisTemplate.getValueSerializer();
        String channel = String.valueOf(serializer.deserialize(message.getChannel()));
        String body = String.valueOf(serializer.deserialize(message.getBody()));
        //key过期监听
        if (StrUtil.format("__keyevent@{}__:expired", redisConfigProperties.getDatabase()).equals(channel)) {
            //订单自动取消
            if (body.contains(ShopConstants.REDIS_ORDER_OUTTIME_UNPAY)) {
                body = body.replace(ShopConstants.REDIS_ORDER_OUTTIME_UNPAY, "");
                log.info("body:{}", body);
                String orderId = body;
                StoreOrder order = storeOrderService
                        .getOne(new QueryWrapper<StoreOrder>().eq("id", orderId).eq("is_del", 0).eq("paid", 0));
                //只有待支付的订单能取消
                if (order != null) {
                    storeOrderService.cancelOrderByTask(Integer.valueOf(orderId));

                    log.info("订单id:{},未在规定时间支付取消成功", body);
                }
            }
            //订单自动收货
            if (body.contains(ShopConstants.REDIS_ORDER_OUTTIME_UNCONFIRM)) {
                body = body.replace(ShopConstants.REDIS_ORDER_OUTTIME_UNCONFIRM, "");
                log.info("body:{}", body);
                String orderId = body;
                StoreOrder order = storeOrderService.getOne(
                        new QueryWrapper<StoreOrder>().eq("id", orderId).eq("is_del", 0).eq("paid", 1).eq("status", 1));
                //只有待收货的订单能收货
                if (order != null) {
                    storeOrderService.takeOrder(order.getOrderId(), 0);
                    log.info("订单id:{},自动收货成功", body);
                }
            }
        }

    }
}
