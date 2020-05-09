package co.lq.modules.shop.web.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 店铺类目关联表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2020-04-23
 */
@Data
@ApiModel(value = "StoreCatalogRelationQueryVo对象", description = "店铺类目关联表查询参数")
public class StoreCatalogRelationQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺id")
    private Long              id;

    @ApiModelProperty(value = "所属类目")
    private Long              catalogId;

    /** 所属店铺 */
    @ApiModelProperty(value = "所属店铺")
    private Long              storeId;

    @ApiModelProperty(value = "添加时间")
    private Timestamp         addTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp         modifyTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean           deleted;

}
