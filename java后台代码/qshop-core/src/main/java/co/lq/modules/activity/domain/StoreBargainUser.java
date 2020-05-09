package co.lq.modules.activity.domain;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * 砍价数据表
 *
 * @author songbin
 * @since 2020年3月22日 下午3:05:03
 */
@Entity
@Data
@Table(name = "store_bargain_user")
public class StoreBargainUser implements Serializable {

    // 砍价产品ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    // 参与砍价的用户ID
    @Column(name = "uid")
    private Long       uid;

    // 砍价产品id
    @Column(name = "bargain_id")
    private Long       bargainId;

    // 砍价底价
    @Column(name = "bargain_price_min")
    private BigDecimal bargainPriceMin;

    // 砍价
    @Column(name = "bargain_price")
    private BigDecimal bargainPrice;

    // 成交价格
    @Column(name = "price")
    private BigDecimal price;

    // 状态
    @Column(name = "status")
    private Byte       status;

    // 砍价结束时间
    @Column(name = "add_time", nullable = false)
    private Integer    addTime;

    // 是否删除
    @Column(name = "is_del")
    private Integer    isDel;

    // 店铺id
    @Column(name = "store_id", nullable = false)
    private Long       storeId;

    public void copy(StoreBargainUser source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
