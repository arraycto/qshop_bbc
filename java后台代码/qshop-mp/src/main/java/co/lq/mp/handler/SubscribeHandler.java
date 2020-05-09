package co.lq.mp.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.ObjectUtil;
import co.lq.mp.domain.WechatReply;
import co.lq.mp.service.WechatReplyService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

@Component
public class SubscribeHandler extends AbstractHandler {

    @Autowired
    private WechatReplyService wechatReplyService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager)
            throws WxErrorException {

        String str = "你好，欢迎关注qshop!";
        WechatReply wechatReply = wechatReplyService.isExist("subscribe");
        if (!ObjectUtil.isNull(wechatReply)) {
            str = JSONObject.parseObject(wechatReply.getData()).getString("content");
        }

        try {
            WxMpXmlOutMessage msg = WxMpXmlOutMessage.TEXT()
                    .content(str)
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser())
                    .build();
            return msg;
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

}
