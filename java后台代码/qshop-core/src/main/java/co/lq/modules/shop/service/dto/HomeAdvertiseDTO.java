package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-13
 */
@Data
public class HomeAdvertiseDTO implements Serializable {

    private Long      id;

    private String    name;

    /** 轮播位置：0->PC首页轮播；1->app首页轮播, 2-h5、小程序 */
    private Integer   type;

    private String    pic;

    private Timestamp startTime;

    private Timestamp endTime;

    /** 上下线状态：0->下线；1->上线 */
    private Integer   status;

    /** 点击数 */
    private Integer   clickCount;

    /** 下单数 */
    private Integer   orderCount;

    /** 链接地址 */
    private String    url;

    /** 备注 */
    private String    note;

    /** 排序 */
    private Integer   sort;

    /** 广告所属店铺 */
    private Long      storeId;
}
