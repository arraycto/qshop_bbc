package co.lq.modules.shop.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author billy
 * @date 2019-11-02
 */
@Data
public class StoreOrderStatusDTO implements Serializable {

    private Long    id;

    // 订单id
    private Long    oid;

    // 操作类型
    private String  changeType;

    // 操作备注
    private String  changeMessage;

    // 操作时间
    private Integer changeTime;
}
