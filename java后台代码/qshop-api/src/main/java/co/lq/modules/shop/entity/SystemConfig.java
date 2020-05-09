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
 * 配置表
 * </p>
 *
 * @author billy
 * @since 2019-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SystemConfig对象", description = "配置表")
public class SystemConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "配置id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer           id;

    @ApiModelProperty(value = "字段名称")
    private String            menuName;

    @ApiModelProperty(value = "默认值")
    private String            value;

    @ApiModelProperty(value = "排序")
    private Integer           sort;

    @ApiModelProperty(value = "是否隐藏")
    private Boolean           status;

}
