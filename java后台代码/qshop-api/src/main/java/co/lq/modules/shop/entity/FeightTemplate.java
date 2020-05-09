package co.lq.modules.shop.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 运费模版
 * </p>
 *
 * @author billy
 * @since 2020-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "FeightTemplate对象", description = "运费模版")
public class FeightTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
