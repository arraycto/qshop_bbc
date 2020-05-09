package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-10
 */
@Data
public class ProductVertifyRecordDTO implements Serializable {

    private Long      id;

    private Integer   productId;

    /** 审核人 */
    private String    vertifier;

    private Integer   status;

    /** 反馈详情 */
    private String    detail;

    /** 所属店铺 */
    private Long      storeId;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer   deleted;
}
