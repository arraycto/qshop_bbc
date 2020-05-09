package co.lq.modules.activity.domain;

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
 * @date 2019-11-18
 */
@Entity
@Data
@Table(name = "store_visit")
public class StoreVisit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long    id;

    // 产品ID
    @Column(name = "product_id")
    private Long    productId;

    // 产品类型
    @Column(name = "product_type")
    private String  productType;

    // 产品分类ID
    @Column(name = "cate_id")
    private Long    cateId;

    // 产品类型
    @Column(name = "type")
    private String  type;

    // 用户ID
    @Column(name = "uid")
    private Long    uid;

    // 访问次数
    @Column(name = "count")
    private Integer count;

    // 备注描述
    @Column(name = "content")
    private String  content;

    // 添加时间
    @Column(name = "add_time")
    private Integer addTime;

    // 店铺id
    @Column(name = "store_id", nullable = false)
    private Long    storeId;

    public void copy(StoreVisit source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
