package co.lq.modules.shop.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Data
public class StoreGiftsDTO implements Serializable {

    private Long id;

    /** 类别 */
    private Long categoryId;

    /** 图片 */
    private String icon;

    /** 标题 */
    private String title;

    /** 状态 */
    private Integer showStatus;

    /** 内容 */
    private String content;

    /** 所属店铺 */
    private Integer storeId;

    /** 1 赠品 2 活动商品 */
    private Integer type;

    private BigDecimal price;

    private Integer stock;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer deleted;
}