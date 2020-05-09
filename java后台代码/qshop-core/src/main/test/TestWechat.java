import static me.chanjar.weixin.common.api.WxConsts.EventType;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;

import co.lq.mp.handler.KfSessionHandler;
import co.lq.mp.handler.LocationHandler;
import co.lq.mp.handler.LogHandler;
import co.lq.mp.handler.MenuHandler;
import co.lq.mp.handler.MsgHandler;
import co.lq.mp.handler.ScanHandler;
import co.lq.mp.handler.StoreCheckNotifyHandler;
import co.lq.mp.handler.SubscribeHandler;
import co.lq.mp.handler.UnsubscribeHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;

/**
 * TODO 类实现描述
 *
 * @author songbin
 * @since 2020年2月26日 下午3:24:14
 */
public class TestWechat {

    private static LogHandler              logHandler;
    private static KfSessionHandler        kfSessionHandler;
    private static StoreCheckNotifyHandler storeCheckNotifyHandler;
    private static LocationHandler         locationHandler;
    private static MenuHandler             menuHandler;
    private static MsgHandler              msgHandler;
    private static UnsubscribeHandler      unsubscribeHandler;
    private static SubscribeHandler        subscribeHandler;
    private static ScanHandler             scanHandler;

    @Test
    public void testWechat() {
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId("wx6175e9ad228d4514");
        configStorage.setSecret("a134079318fda6d0d5fa1ae1020c22c9");
        configStorage.setToken("9f75a66e5cfd08e28952663caf999f5a");
        configStorage.setAesKey("VSQnQmsB64pomsVjRcsLcjTuvyDB8GnJ52ebbeBelNq");
        Map<String, WxMpService> mpServices = Maps.newHashMap();
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(configStorage);
        mpServices.put("wx6175e9ad228d4514", wxMpService);
        Map<String, WxMpMessageRouter> routers = Maps.newHashMap();
        routers.put("wx6175e9ad228d4514", newRouter(wxMpService));
        try {
            wxMpService.createJsapiSignature("https://h5-b2c.leiqukeji.com/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static WxMpMessageRouter newRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(logHandler).next();

        // 接收客服会话管理事件
        newRouter.rule()
                .async(false)
                .msgType(XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
                .handler(kfSessionHandler)
                .end();
        newRouter.rule()
                .async(false)
                .msgType(XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
                .handler(kfSessionHandler)
                .end();
        newRouter.rule()
                .async(false)
                .msgType(XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
                .handler(kfSessionHandler)
                .end();

        // 门店审核事件
        newRouter.rule()
                .async(false)
                .msgType(XmlMsgType.EVENT)
                .event(WxMpEventConstants.POI_CHECK_NOTIFY)
                .handler(storeCheckNotifyHandler)
                .end();

        // 自定义菜单事件
        newRouter.rule()
                .async(false)
                .msgType(XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.CLICK)
                .handler(menuHandler)
                .end();

        // 点击菜单连接事件
        newRouter.rule()
                .async(false)
                .msgType(XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.VIEW)
                .handler(menuHandler)
                .end();

        // 扫码事件
        newRouter.rule()
                .async(false)
                .msgType(XmlMsgType.EVENT)
                .event(EventType.SCANCODE_WAITMSG)
                .handler(menuHandler)
                .end();

        // 关注事件
        newRouter.rule()
                .async(false)
                .msgType(XmlMsgType.EVENT)
                .event(EventType.SUBSCRIBE)
                .handler(subscribeHandler)
                .end();

        // 取消关注事件
        newRouter.rule()
                .async(false)
                .msgType(XmlMsgType.EVENT)
                .event(EventType.UNSUBSCRIBE)
                .handler(unsubscribeHandler)
                .end();

        // 上报地理位置事件
        newRouter.rule()
                .async(false)
                .msgType(XmlMsgType.EVENT)
                .event(EventType.LOCATION)
                .handler(locationHandler)
                .end();

        // 默认
        newRouter.rule().async(false).handler(msgHandler).end();

        return newRouter;
    }
}
