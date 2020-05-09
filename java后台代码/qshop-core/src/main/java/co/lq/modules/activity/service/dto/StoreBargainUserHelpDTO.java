package co.lq.modules.activity.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * TODO 类实现描述
 *
 * @author songbin
 * @since 2020年3月22日 下午4:39:31
 */
@Data
public class StoreBargainUserHelpDTO implements Serializable {

    // 砍价产品ID
    private Long       id;

    // 参与砍价的用户ID
    private Long       uid;

    // 砍价产品id
    private Long       bargainId;

    // 用户参与砍价表id
    private Long       bargainUserId;

    // 帮助砍价多少金额
    private BigDecimal price;

    // 砍价结束时间
    private Integer    addTime;

    // 店铺id
    private Long       storeId;
}
