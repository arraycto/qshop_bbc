package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-27
 */
@Entity
@Data
@Table(name = "basic_gifts")
public class BasicGifts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long      id;

    @Column(name = "name")
    private String    name;

    /** 1 有效2 无效 */
    @Column(name = "status")
    private Integer   status;

    /** 活动对象1全部用户2 会员级别 */
    @Column(name = "activi_user")
    private Integer   activiUser;

    /** 活动商品 */
    @Column(name = "activi_goods")
    private Long      activiGoods;

    /** 1 首购礼 2 满 购礼 3 单品礼赠 */
    @Column(name = "big_type")
    private Integer   bigType;

    /** 首购礼 1第一单获取 2所有订单获取 ； 满购礼1选赠礼 获取规则 2满赠礼 */
    @Column(name = "small_type")
    private Integer   smallType;

    /** 规则 */
    @Column(name = "rules")
    private String    rules;

    /** 部分商品列表 */
    @Column(name = "goods_ids")
    private String    goodsIds;

    /** 会员级别 */
    @Column(name = "user_level")
    private String    userLevel;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    /** 赠品 */
    @Column(name = "gift_ids")
    private String    giftIds;

    /** 添加时间 */
    @Column(name = "add_time", nullable = false)
    @NotNull
    private Timestamp addTime;

    /** 更新时间 */
    @Column(name = "modify_time", nullable = false)
    @NotNull
    private Timestamp modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted", nullable = false)
    @NotNull
    private Integer   deleted;

    @Column(name = "note")
    private String    note;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Long      storeId;

    public void copy(BasicGifts source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
