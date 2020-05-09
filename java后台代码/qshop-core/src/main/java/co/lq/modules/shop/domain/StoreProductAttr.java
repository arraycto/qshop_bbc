package co.lq.modules.shop.domain;

import java.io.Serializable;
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
@Table(name = "store_product_attr")
public class StoreProductAttr implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long    id;
    // 商品ID
    @Column(name = "product_id", nullable = false)
    private Long    productId;

    // 属性名
    @Column(name = "attr_name", nullable = false)
    private String  attrName;

    // 属性值
    @Column(name = "attr_values", nullable = false)
    private String  attrValues;

    /**
     * 类目属性id
     */
    @Column(name = "catalog_attr_id", nullable = false)
    private Long    catalogAttrId;

    /**
     * 属性的类型
     */
    @Column(name = "type", nullable = false)
    private Integer type;

    /** 添加时间 */
    @Column(name = "add_time")
    private Date    addTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Date    modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted")
    private Integer deleted;

    public void copy(StoreProductAttr source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
