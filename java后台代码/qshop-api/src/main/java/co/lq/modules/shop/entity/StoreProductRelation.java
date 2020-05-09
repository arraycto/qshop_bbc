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
 * 商品点赞和收藏表
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreProductRelation对象", description = "商品点赞和收藏表")
public class StoreProductRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "用户ID")
    private Long              uid;

    @ApiModelProperty(value = "商品ID")
    private Long              productId;

    @ApiModelProperty(value = "类型(收藏(collect）、点赞(like))")
    private String            type;

    @ApiModelProperty(value = "某种类型的商品(普通商品、秒杀商品)")
    private String            category;

    @ApiModelProperty(value = "添加时间")
    private Integer           addTime;

}
