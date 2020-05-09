package co.lq.modules.shop.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-04-05
*/
@Data
public class StoreCollectDTO implements Serializable {

    /** 商品点赞ID */
    private Long id;

    /** 用户ID */
    private Integer uid;

    /** 店铺ID */
    private Long storeId;

    /** 类型(收藏(collect）、点赞(like)) */
    private String type;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer deleted;
}