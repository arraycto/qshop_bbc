package co.lq.mp.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
public abstract class AbstractBuilder {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public abstract WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage, WxMpService service);
}
