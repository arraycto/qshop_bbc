package co.lq.modules.notify.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.qcloudsms.SmsSingleSender;

import co.lq.config.NotifyProperties;
import co.lq.modules.notify.AliyunSmsSender;
import co.lq.modules.notify.NotifyService;
import co.lq.modules.notify.TencentSmsSender;

@Configuration
@EnableConfigurationProperties(NotifyProperties.class)
public class NotifyAutoConfiguration {

    private final NotifyProperties properties;

    public NotifyAutoConfiguration(NotifyProperties properties) {
        this.properties = properties;
    }

    @Bean
    public NotifyService notifyService() {
        NotifyService notifyService = new NotifyService();

        NotifyProperties.Sms smsConfig = properties.getSms();
        if (smsConfig.isEnable()) {
            if (smsConfig.getActive().equals("tencent")) {
                notifyService.setSmsSender(tencentSmsSender());
            } else if (smsConfig.getActive().equals("aliyun")) {
                notifyService.setSmsSender(aliyunSmsSender());
            }

            notifyService.setSmsTemplate(smsConfig.getTemplate());
        }

        return notifyService;
    }

    @Bean
    public TencentSmsSender tencentSmsSender() {
        NotifyProperties.Sms smsConfig = properties.getSms();
        TencentSmsSender smsSender = new TencentSmsSender();
        NotifyProperties.Sms.Tencent tencent = smsConfig.getTencent();
        smsSender.setSender(new SmsSingleSender(tencent.getAppid(), tencent.getAppkey()));
        smsSender.setSign(smsConfig.getSign());
        return smsSender;
    }

    @Bean
    public AliyunSmsSender aliyunSmsSender() {
        NotifyProperties.Sms smsConfig = properties.getSms();
        AliyunSmsSender smsSender = new AliyunSmsSender();
        NotifyProperties.Sms.Aliyun aliyun = smsConfig.getAliyun();
        smsSender.setSign(smsConfig.getSign());
        smsSender.setRegionId(aliyun.getRegionId());
        smsSender.setAccessKeyId(aliyun.getAccessKeyId());
        smsSender.setAccessKeySecret(aliyun.getAccessKeySecret());
        return smsSender;
    }
}
