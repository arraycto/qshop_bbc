package co.lq.modules.shop.entity;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author songbin
 * @since 2020年3月31日 下午8:47:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SellerRole对象", description = "店铺角色表参数")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺角色表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "角色名")
    private String            name;

    /** 数据权限类型 全部 、 本级 、 自定义 */
    @ApiModelProperty(value = "数据权限类型")
    private String            dataScope        = "本级";

    /** 数值越小，级别越大 */
    @ApiModelProperty(value = "等级")
    private Integer           level            = 3;

    @ApiModelProperty(value = "备注")
    private String            remark;

    /** 权限 */
    @ApiModelProperty(value = "权限")
    private String            permission;

    @ApiModelProperty(value = "创建时间")
    private Timestamp         createTime;

    // 所属门店
    @ApiModelProperty(value = "所属门店")
    private Long              storeId;
}
