package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2020-04-23
 */
@Data
public class StoreCatalogRelationDTO implements Serializable {

    /** 店铺id */
    private Long      id;

    /** 所属类目 */
    private Long      catalogId;

    /**
     * 所属店铺
     */
    private Long      storeId;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer   deleted;
}
