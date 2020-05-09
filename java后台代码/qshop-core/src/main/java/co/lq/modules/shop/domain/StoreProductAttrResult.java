package co.lq.modules.shop.domain;

import java.io.Serializable;

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
@Table(name = "store_product_attr_result")
public class StoreProductAttrResult implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long    id;

    // 商品ID
    @Column(name = "product_id", nullable = false)
    private Long    productId;

    // 商品属性参数
    @Column(name = "result", nullable = false)
    private String  result;

    // 上次修改时间
    @Column(name = "change_time", nullable = false)
    private Integer changeTime;

    public void copy(StoreProductAttrResult source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
