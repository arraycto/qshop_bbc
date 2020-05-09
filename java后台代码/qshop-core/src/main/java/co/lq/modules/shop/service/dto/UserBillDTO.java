package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @author billy
 * @date 2019-11-06
 */
@Data
public class UserBillDTO implements Serializable {

    // 用户账单id
    private Long       id;

    // 用户uid
    private Long       uid;

    // 关联id
    private String     linkId;

    // 0 = 支出 1 = 获得
    private Integer    pm;

    // 账单标题
    private String     title;

    // 明细种类
    private String     category;

    // 明细类型
    private String     type;

    // 明细数字
    private BigDecimal number;

    // 剩余
    private BigDecimal balance;

    // 备注
    private String     mark;

    // 添加时间
    private Integer    addTime;

    // 0 = 带确定 1 = 有效 -1 = 无效
    private Integer    status;

    //所属店铺
    private Long       storeId;
}
