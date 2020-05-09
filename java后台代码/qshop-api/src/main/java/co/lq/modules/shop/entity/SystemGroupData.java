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
 * 组合数据详情表
 * </p>
 *
 * @author billy
 * @since 2019-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SystemGroupData对象", description = "组合数据详情表")
public class SystemGroupData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组合数据详情ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "对应的数据名称")
    private String            groupName;

    @ApiModelProperty(value = "数据组对应的数据值（json数据）")
    private String            value;

    @ApiModelProperty(value = "添加数据时间")
    private Integer           addTime;

    @ApiModelProperty(value = "数据排序")
    private Integer           sort;

    @ApiModelProperty(value = "状态（1：开启；2：关闭；）")
    private Boolean           status;

}
