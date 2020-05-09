package co.lq.mp.config;

import static me.chanjar.weixin.common.api.WxConsts.EventType;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Maps;

import co.lq.constant.ShopConstants;
import co.lq.mp.handler.KfSessionHandler;
import co.lq.mp.handler.LocationHandler;
import co.lq.mp.handler.LogHandler;
import co.lq.mp.handler.MenuHandler;
import co.lq.mp.handler.MsgHandler;
import co.lq.mp.handler.NullHandler;
import co.lq.mp.handler.ScanHandler;
import co.lq.mp.handler.StoreCheckNotifyHandler;
import co.lq.mp.handler.SubscribeHandler;
import co.lq.mp.handler.UnsubscribeHandler;
import co.lq.utils.RedisUtil;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;

/**
 * 公众号配置
 *
 * @author billy
 * @date 2020/01/20
 */
@Configuration
public class WxMpConfiguration {

    private static Map<String, WxMpService>       mpServices = Maps.newHashMap();
    private static Map<String, WxMpMessageRouter> routers    = Maps.newHashMap();

    private static LogHandler                     logHandler;
    private static NullHandler                    nullHandler;
    private static KfSessionHandler               kfSessionHandler;
    private static StoreCheckNotifyHandler        storeCheckNotifyHandler;
    private static LocationHandler                locationHandler;
    private static MenuHandler                    menuHandler;
    private static MsgHandler                     msgHandler;
    private static UnsubscribeHandler             unsubscribeHandler;
    private static SubscribeHandler               subscribeHandler;
    private static ScanHandler                    scanHandler;

    public WxMpConfiguration(LogHandler logHandler, NullHandler nullHandler, KfSessionHandler kfSessionHandler,
                             StoreCheckNotifyHandler storeCheckNotifyHandler, LocationHandler locationHandler,
                             MenuHandler menuHandler, MsgHandler msgHandler, UnsubscribeHandler unsubscribeHandler,
                             SubscribeHandler subscribeHandler, ScanHandler scanHandler) {
        this.logHandler = logHandler;
        this.nullHandler = nullHandler;
        this.kfSessionHandler = kfSessionHandler;
        this.storeCheckNotifyHandler = storeCheckNotifyHandler;
        this.locationHandler = locationHandler;
        this.menuHandler = menuHandler;
        this.msgHandler = msgHandler;
        this.unsubscribeHandler = unsubscribeHandler;
        this.subscribeHandler = subscribeHandler;
        this.scanHandler = scanHandler;
    }

    /**
     * 获取WxMpService
     *
     * @return
     */
    public static WxMpService getWxMpService() {

        WxMpService wxMpService = mpServices.get(ShopConstants.QSHOP_WEIXIN_MP_SERVICE);
        //增加一个redis标识
        if (wxMpService == null || RedisUtil.get(ShopConstants.QSHOP_WEIXIN_MP_SERVICE) == null) {
            WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
            configStorage.setAppId(RedisUtil.get("wechat_appid"));
            configStorage.setSecret(RedisUtil.get("wechat_appsecret"));
            configStorage.setToken(RedisUtil.get("wechat_token"));
            configStorage.setAesKey(RedisUtil.get("wechat_encodingaeskey"));
            wxMpService = new WxMpServiceImpl();
            wxMpService.setWxMpConfigStorage(configStorage);
            mpServices.put(ShopConstants.QSHOP_WEIXIN_MP_SERVICE, wxMpService);
            routers.put(ShopConstants.QSHOP_WEIXIN_MP_SERVICE, newRouter(wxMpService));

            //增加标识
            RedisUtil.set(ShopConstants.QSHOP_WEIXIN_MP_SERVICE, "yshop");
        }
        return wxMpService;
    }

    /**
     * 移除WxMpService
     */
    public static void removeWxMpService() {
        RedisUtil.del(ShopConstants.QSHOP_WEIXIN_MP_SERVICE);
        mpServices.remove(ShopConstants.QSHOP_WEIXIN_MP_SERVICE);
        routers.remove(ShopConstants.QSHOP_WEIXIN_MP_SERVICE);
    }

    /**
     * 获取WxMpMessageRouter
     */
    public static WxMpMessageRouter getWxMpMessageRouter() {
        WxMpMessageRouter wxMpMessageRouter = routers.get(ShopConstants.QSHOP_WEIXIN_MP_SERVICE);
        return wxMpMessageRouter;
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
