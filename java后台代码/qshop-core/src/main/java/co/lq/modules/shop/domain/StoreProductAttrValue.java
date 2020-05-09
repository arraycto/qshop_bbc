package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2019-10-13
 */
@Entity
@Data
@Table(name = "store_product_attr_value")
public class StoreProductAttrValue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    // 商品ID
    @Column(name = "product_id", nullable = false)
    private Long       productId;

    // 商品属性索引值 (attr_value|attr_value[|....])
    @Column(name = "suk", nullable = false)
    private String     suk;

    // 属性对应的库存
    @Column(name = "stock", nullable = false)
    private Integer    stock;

    // 销量
    @Column(name = "sales", nullable = false)
    private Integer    sales;

    // 属性金额
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    // 图片
    @Column(name = "image")
    private String     image;

    // 唯一值
    @Column(name = "`unique`", nullable = false)
    private String     unique;

    /**
     * 条码
     */
    @Column(name = "`bar_code`")
    private String     barCode;

    // 成本价
    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    /** 添加时间 */
    @Column(name = "add_time")
    private Date       addTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Date       modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted")
    private Integer    deleted;

    public void copy(StoreProductAttrValue source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
