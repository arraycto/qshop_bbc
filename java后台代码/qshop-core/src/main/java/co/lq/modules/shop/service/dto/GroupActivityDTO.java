package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

/**
 * @author billy
 * @date 2020-04-02
 */
@Data
public class GroupActivityDTO implements Serializable {

    private Long          id;

    /** 活动名称 */
    private String        name;

    /** 活动价格 */
    private BigDecimal    price;

    /** 运费 */
    private BigDecimal    transfee;

    /** 活动状态 1 开启 2 关闭 */
    private Integer       status;

    /** 1 买家承担 2 卖家承担 */
    private Integer       feestatus;

    /** 所属店铺 */
    private Long          storeId;

    private String        goodsIds;

    private String        pic;

    private BigDecimal    originprice;

    /** 添加时间 */
    private Timestamp     addTime;

    /** 更新时间 */
    private Timestamp     modifyTime;

    /** 逻辑删除 */
    private Integer       deleted;

    List<StoreProductDTO> productList;
}
