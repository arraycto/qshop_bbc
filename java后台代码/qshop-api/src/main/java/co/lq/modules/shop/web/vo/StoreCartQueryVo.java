package co.lq.modules.shop.web.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 购物车表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2019-10-25
 */
@Data
@ApiModel(value = "StoreCartQueryVo对象", description = "购物车表查询参数")
public class StoreCartQueryVo implements Serializable {
    private static final long   serialVersionUID = 1L;

    @ApiModelProperty(value = "购物车表ID")
    private Long                id;

    @ApiModelProperty(value = "购物车表ID字符")
    private String              strId;

    @ApiModelProperty(value = "用户ID")
    private Long                uid;

    @ApiModelProperty(value = "类型")
    private String              type;

    @ApiModelProperty(value = "商品ID")
    private Long                productId;

    @ApiModelProperty(value = "商品属性")
    private String              productAttrUnique;

    @ApiModelProperty(value = "商品数量")
    private Integer             cartNum;

    @ApiModelProperty(value = "添加时间")
    private Integer             addTime;

    @ApiModelProperty(value = "拼团id")
    private Long                combinationId;

    @ApiModelProperty(value = "秒杀产品ID")
    private Long                seckillId;

    @ApiModelProperty(value = "砍价id")
    private Long                bargainId;

    @ApiModelProperty(value = "商品明细")
    private StoreProductQueryVo productInfo;

    private Double              costPrice;

    private Double              truePrice;

    private Integer             trueStock;

    private Double              vipTruePrice;

    private String              unique;

    private Integer             isReply;

    @ApiModelProperty(name = "店铺id")
    private Long                storeId;

    @ApiModelProperty(name = "店铺名称")
    private String              storeName;

    @ApiModelProperty(name = "店铺门头照")
    private String              storeImage;

    @ApiModelProperty(name = "积分商品id")
    private Long                integralId;

    @ApiModelProperty(name = "积分商品所需积分")
    private Integer             integral;

    @ApiModelProperty(name = "优惠价格")
    private Double              promotionPrice;
}
