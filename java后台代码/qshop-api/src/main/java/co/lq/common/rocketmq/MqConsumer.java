package co.lq.common.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQListener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.util.ObjectUtil;
import co.lq.modules.order.entity.StoreOrder;
import co.lq.modules.order.service.StoreOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName 消费者
 * @Author billy
 * @Date 2020/1/1
 **/
//@Component
//@RocketMQMessageListener(
//        topic = "qshop-topic",
//        consumerGroup = "qshop-group",
//        selectorExpression = "*"
//)
@Slf4j
@AllArgsConstructor
public class MqConsumer implements RocketMQListener<String> {

    private final StoreOrderService storeOrderService;

    @Override
    public void onMessage(String msg) {
        log.info("系统开始处理延时任务---订单超时未付款---订单id:" + msg);

        Integer id = Integer.valueOf(msg);

        StoreOrder order = storeOrderService
                .getOne(new QueryWrapper<StoreOrder>().eq("id", id).eq("is_del", 0).eq("paid", 0));

        if (ObjectUtil.isNull(order)) {
            return;
        }

        storeOrderService.cancelOrderByTask(id);

        log.info("=====处理成功======");

    }
}
