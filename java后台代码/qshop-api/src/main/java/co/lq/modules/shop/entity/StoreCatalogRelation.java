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
 * <p>
 * 店铺类目关联表
 * </p>
 *
 * @author billy
 * @since 2020-04-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreCatalogRelation对象", description = "店铺类目关联表")
public class StoreCatalogRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    /** 所属店铺 */
    @ApiModelProperty(value = "所属店铺")
    private Long              storeId;

    @ApiModelProperty(value = "所属类目")
    private Long              catalogId;

    @ApiModelProperty(value = "添加时间")
    private Timestamp         addTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp         modifyTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean           deleted;

}
