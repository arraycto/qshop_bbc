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
 * 商品属性详情表
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreProductAttrResult对象", description = "商品属性详情表")
public class StoreProductAttrResult extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "商品ID")
    private Long              productId;

    @ApiModelProperty(value = "商品属性参数")
    private String            result;

    @ApiModelProperty(value = "上次修改时间")
    private Integer           changeTime;

}
