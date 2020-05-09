package co.lq.modules.shop.entity;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色用户关联表
 *
 * @author songbin
 * @since 2020年3月31日 下午9:02:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SellerRole对象", description = "店铺角色表参数")
public class SystemUsersRoles extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long              userId;

    @ApiModelProperty(value = "角色id")
    private Long              roleId;
}
