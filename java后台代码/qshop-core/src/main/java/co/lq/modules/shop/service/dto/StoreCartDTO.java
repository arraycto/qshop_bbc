package co.lq.modules.shop.service.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 购物车
 *
 * @author songbin
 * @since 2020年4月7日 下午8:02:50
 */
@Data
public class StoreCartDTO implements Serializable {
    private static final long    serialVersionUID = 1L;

    private Long                 id;

    private String               strId;

    private Long                 uid;

    private String               type;

    private Long                 productId;

    private String               productAttrUnique;

    private Integer              cartNum;

    private Integer              addTime;

    private Long                 combinationId;

    private Long                 seckillId;

    private Long                 bargainId;

    private StoreProductQueryDTO productInfo;

    private Double               costPrice;

    private Double               truePrice;

    private Integer              trueStock;

    private Double               vipTruePrice;

    private String               unique;

    private Integer              isReply;

    @ApiModelProperty(name = "店铺id")
    private Long                 storeId;

    @ApiModelProperty(name = "店铺名称")
    private String               storeName;

    @ApiModelProperty(name = "店铺门头照")
    private String               storeImage;
}
