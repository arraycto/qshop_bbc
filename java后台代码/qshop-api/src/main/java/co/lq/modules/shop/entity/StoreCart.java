package co.lq.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author billy
 * @since 2019-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreCart对象", description = "购物车表")
public class StoreCart extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "购物车表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "用户ID")
    private Long              uid;

    @ApiModelProperty(value = "类型")
    private String            type;

    @ApiModelProperty(value = "商品ID")
    private Long              productId;

    @ApiModelProperty(value = "商品属性")
    private String            productAttrUnique;

    @ApiModelProperty(value = "商品数量")
    private Integer           cartNum;

    @ApiModelProperty(value = "添加时间")
    private Integer           addTime;

    @ApiModelProperty(value = "0 = 未购买 1 = 已购买")
    private Integer           isPay;

    @ApiModelProperty(value = "是否删除")
    private Integer           isDel;

    @ApiModelProperty(value = "是否为立即购买")
    private Integer           isNew;

    @ApiModelProperty(value = "拼团id")
    private Long              combinationId;

    @ApiModelProperty(value = "秒杀产品ID")
    private Long              seckillId;

    @ApiModelProperty(value = "砍价id")
    private Long              bargainId;

    @ApiModelProperty(name = "店铺id")
    private Long              storeId;

    @ApiModelProperty(name = "积分商品id")
    private Long              integralId;

}
