package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-11
 */
@Data
public class StoreComplaintDTO implements Serializable {

    /** 分销商品id */
    private Long      id;

    /** 会员id */
    private Long      uid;

    private String    shopName;

    /** 投诉原因 */
    private String    reason;

    /** 投诉内容 */
    private String    desc;

    /** 所属店铺 */
    private Long      storeId;

    private String    nickname;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer   deleted;
}
