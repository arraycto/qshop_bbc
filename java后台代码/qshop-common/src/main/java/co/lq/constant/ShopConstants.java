package co.lq.constant;

/**
 * 商城统一常量
 *
 * @author billy
 * @since 2020-02-27
 */
public interface ShopConstants {

    /**
     * 订单自动取消时间（分钟）
     */
    long   ORDER_OUTTIME_UNPAY           = 30;
    /**
     * 订单自动收货时间（天）
     */
    long   ORDER_OUTTIME_UNCONFIRM       = 7;
    /**
     * redis订单未付款key
     */
    String REDIS_ORDER_OUTTIME_UNPAY     = "order:unpay:";
    /**
     * redis订单收货key
     */
    String REDIS_ORDER_OUTTIME_UNCONFIRM = "order:unconfirm:";

    /**
     * 微信支付service
     */
    String QSHOP_WEIXIN_PAY_SERVICE      = "qshop_weixin_pay_service";

    /**
     * 微信支付小程序service
     */
    String QSHOP_WEIXIN_MINI_PAY_SERVICE = "qshop_weixin_mini_pay_service";

    /**
     * 微信公众号service
     */
    String QSHOP_WEIXIN_MP_SERVICE       = "qshop_weixin_mp_service";

    /**
     * 商城默认密码
     */
    String QSHOP_DEFAULT_PWD             = "123456";

    /**
     * 商城默认注册图片
     */
    String QSHOP_DEFAULT_AVATAR          = "https://reslqmall.iyyou.com/bbc/static/qshop/images/logo.a19ca35b.png";

    /**
     * 腾讯地图地址解析
     */
    String QQ_MAP_URL                    = "https://apis.map.qq.com/ws/geocoder/v1/";

    /**
     * redis首页键
     */
    String QSHOP_REDIS_INDEX_KEY         = "qshop:index_data";

}
