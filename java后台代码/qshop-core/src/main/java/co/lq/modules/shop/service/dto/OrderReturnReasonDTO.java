package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-27
 */
@Data
public class OrderReturnReasonDTO implements Serializable {

    private Long      id;

    /** 退货类型 */
    private String    name;

    private Integer   sort;

    /** 状态：0->不启用；1->启用 */
    private Integer   status;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer   deleted;
}
