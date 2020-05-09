package co.lq.modules.shop.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-04-13
*/
@Data
public class StoreWithdrawRecordDTO implements Serializable {

    /** 序号 */
    private Long id;

    /** 提现申请ID */
    private Long storeWithdrawId;

    /** 审核人 */
    private String vertifier;

    private Integer status;

    /** 反馈详情 */
    private String detail;

    /** 所属店铺 */
    private Long storeId;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer deleted;
}