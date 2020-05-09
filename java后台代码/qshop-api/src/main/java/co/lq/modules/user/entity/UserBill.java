package co.lq.modules.user.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户账单表
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserBill对象", description = "用户账单表")
public class UserBill extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户账单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "用户uid")
    private Long              uid;

    @ApiModelProperty(value = "关联id")
    private String            linkId;

    @ApiModelProperty(value = "0 = 支出 1 = 获得")
    private Integer           pm;

    @ApiModelProperty(value = "账单标题")
    private String            title;

    @ApiModelProperty(value = "明细种类")
    private String            category;

    @ApiModelProperty(value = "明细类型")
    private String            type;

    @ApiModelProperty(value = "明细数字")
    private BigDecimal        number;

    @ApiModelProperty(value = "剩余")
    private BigDecimal        balance;

    @ApiModelProperty(value = "备注")
    private String            mark;

    @ApiModelProperty(value = "添加时间")
    private Integer           addTime;

    @ApiModelProperty(value = "0 = 带确定 1 = 有效 -1 = 无效")
    private Integer           status;

}
