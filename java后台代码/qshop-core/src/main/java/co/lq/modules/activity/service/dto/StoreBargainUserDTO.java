package co.lq.modules.activity.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 砍价
 *
 * @author songbin
 * @since 2020年3月22日 下午3:55:03
 */
@Data
public class StoreBargainUserDTO implements Serializable {

    // 砍价产品ID
    private Long       id;

    // 参与砍价的用户ID
    private Long       uid;

    // 优惠券前台领取ID
    private Long       bargainId;

    // 砍价底价
    private BigDecimal bargainPriceMin;

    // 砍价
    private BigDecimal bargainPrice;

    // 成交价格
    private BigDecimal price;

    // 状态
    private Byte       status;

    // 砍价结束时间
    private Integer    addTime;

    // 是否删除
    private Byte       isDel;

    // 店铺id
    private Long       storeId;
}
