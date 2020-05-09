package co.lq.modules.shop.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Data
public class BasicGiftsDTO implements Serializable {

    private Long id;

    private String name;

    /** 1 有效2 无效 */
    private Integer status;

    /** 活动对象1全部用户2 会员级别 */
    private Integer activiUser;

    /** 活动商品 */
    private Long activiGoods;

    /** 1 首购礼 2 满 购礼 3 单品礼赠 */
    private Integer bigType;

    /** 首购礼 1第一单获取 2所有订单获取 ； 满购礼1选赠礼 获取规则 2满赠礼 */
    private Integer smallType;

    /** 规则 */
    private String rules;

    /** 部分商品列表 */
    private String goodsIds;

    /** 会员级别 */
    private String userLevel;

    private Timestamp startTime;

    private Timestamp endTime;

    /** 赠品 */
    private String giftIds;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer deleted;

    private String note;

    /** 所属店铺 */
    private Long storeId;
}