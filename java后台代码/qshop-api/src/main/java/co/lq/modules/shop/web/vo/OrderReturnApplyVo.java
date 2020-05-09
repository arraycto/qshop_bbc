package co.lq.modules.shop.web.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 退换货实际请求参数
 *
 * @author songbin
 * @since 2020年4月4日 下午10:05:34
 */
@Data
@ApiModel(value = "OrderReturnApplyQueryVo对象", description = "订单退货申请查询参数")
public class OrderReturnApplyVo implements Serializable {

    private static final long serialVersionUID = 1;

    @ApiModelProperty(value = "订单id")
    private String            orderId;

    @ApiModelProperty(value = "退换货申请类型")
    private Integer           type;

    @ApiModelProperty(value = "申请项目列表")
    private List<String>      items;

    @ApiModelProperty(value = "申请数量")
    private List<Integer>     itemCounts;

    @ApiModelProperty(value = "售后图片列表")
    private List<String>      images;

    @ApiModelProperty(value = "售后退款金额")
    private String            returnAmount;

    @ApiModelProperty(value = "描述")
    private String            desc;
}
