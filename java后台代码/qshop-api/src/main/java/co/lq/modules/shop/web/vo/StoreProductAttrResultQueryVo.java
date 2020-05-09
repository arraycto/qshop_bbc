package co.lq.modules.shop.web.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 商品属性详情表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2019-10-23
 */
@Data
@ApiModel(value = "StoreProductAttrResultQueryVo对象", description = "商品属性详情表查询参数")
public class StoreProductAttrResultQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long              id;

    @ApiModelProperty(value = "商品ID")
    private Long              productId;

    @ApiModelProperty(value = "商品属性参数")
    private String            result;

    @ApiModelProperty(value = "上次修改时间")
    private Integer           changeTime;

}
