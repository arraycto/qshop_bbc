package co.lq.mp.service.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import co.lq.mp.config.WxMpConfiguration;
import co.lq.mp.service.WxMpTemplateMessageService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Component
public class WxMpTemplateMessageServiceImpl implements WxMpTemplateMessageService {

    @Override
    public String sendWxMpTemplateMessage(String openId, String templateId, String url, Map<String, String> map) {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)
                .templateId(templateId)
                .url(url)
                .build();
        map.forEach((k, v) -> {
            templateMessage.addData(new WxMpTemplateData(k, v, "#000000"));
        });
        String msgId = null;
        WxMpService wxService = WxMpConfiguration.getWxMpService();
        try {
            msgId = wxService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return msgId;
    }
}
