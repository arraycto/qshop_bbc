package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-28
 */
@Data
public class StoreVertifyRecordDTO implements Serializable {

    /** 序号 */
    private Long      id;

    /** 审核人 */
    private String    vertifier;

    /** 反馈详情 */
    private String    detail;

    /** 类目id */
    private Long      storeId;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer   deleted;

    /** 1 通过 2 审核中 3 拒绝 */
    private Integer   status;
}
