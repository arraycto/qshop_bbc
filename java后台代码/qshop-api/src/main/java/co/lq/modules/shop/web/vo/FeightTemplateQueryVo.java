package co.lq.modules.shop.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 运费模版 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2020-05-03
 */
@Data
@ApiModel(value = "FeightTemplateQueryVo对象", description = "运费模版查询参数")
public class FeightTemplateQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long              id;

    @ApiModelProperty(value = "名称")
    private String            name;

    @ApiModelProperty(value = "计费类型:0->按重量；1->按件数")
    private Integer           chargeType;

    @ApiModelProperty(value = "首重kg")
    private BigDecimal        firstWeight;

    @ApiModelProperty(value = "首费（元）")
    private BigDecimal        firstFee;

    @ApiModelProperty(value = "后重量")
    private BigDecimal        continueWeight;

    @ApiModelProperty(value = "后费用")
    private BigDecimal        continmeFee;

    @ApiModelProperty(value = "目的地（省、市）")
    private String            dest;

    @ApiModelProperty(value = "所属店铺")
    private Long              storeId;

    @ApiModelProperty(value = "添加时间")
    private Timestamp         addTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp         modifyTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean           deleted;

}
