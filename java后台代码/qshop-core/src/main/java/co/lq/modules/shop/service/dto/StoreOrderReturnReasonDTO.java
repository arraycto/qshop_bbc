package co.lq.modules.shop.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-29
*/
@Data
public class StoreOrderReturnReasonDTO implements Serializable {

    private Long id;

    /** 退货类型 */
    private String name;

    private Integer sort;

    /** 状态：0->不启用；1->启用 */
    private Integer status;

    /** 所属店铺 */
    private Long storeId;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer deleted;
}