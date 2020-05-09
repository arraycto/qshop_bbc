package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2020-04-02
 */
@Entity
@Data
@Table(name = "group_activity")
public class GroupActivity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    /** 活动名称 */
    @Column(name = "name")
    private String     name;

    /** 活动价格 */
    @Column(name = "price")
    private BigDecimal price;

    /** 运费 */
    @Column(name = "transfee")
    private BigDecimal transfee;

    /** 活动状态 1 开启 2 关闭 */
    @Column(name = "status")
    private Integer    status;

    /** 1 买家承担 2 卖家承担 */
    @Column(name = "feestatus")
    private Integer    feestatus;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Long       storeId;

    @Column(name = "goods_ids")
    private String     goodsIds;

    @Column(name = "pic")
    private String     pic;

    @Column(name = "originprice")
    private BigDecimal originprice;

    /** 添加时间 */
    @Column(name = "add_time")
    private Timestamp  addTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Timestamp  modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted")
    private Integer    deleted;

    @Transient
    List<StoreProduct> productList;

    public void copy(GroupActivity source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
