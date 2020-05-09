package co.lq.common.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

import co.lq.exception.ErrorRequestException;
import lombok.AllArgsConstructor;

/**
 * @ClassName 生成者
 * @Author billy
 * @Date 2020/1/1
 **/
//@Component
@AllArgsConstructor
public class MqProducer {
    //注入rocketMQ的模板
    private final RocketMQTemplate rocketMQTemplate;

    /**
     * 发送延时消息10分钟
     *
     * @param topic
     * @param msg
     */
    public void sendMsg(String topic, String msg) {
        DefaultMQProducer defaultMQProducer = rocketMQTemplate.getProducer();

        Message message = new Message(topic, msg.getBytes());
        message.setDelayTimeLevel(14);

        try {
            defaultMQProducer.send(message);
        } catch (MQClientException e) {
            throw new ErrorRequestException("RocketMQ服务没启动哦");
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
