package co.lq.modules.shop.web.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 店铺收藏表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2020-04-05
 */
@Data
@ApiModel(value = "StoreCollectQueryVo对象", description = "店铺收藏表查询参数")
public class StoreCollectQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品点赞ID")
    private Long              id;

    @ApiModelProperty(value = "用户ID")
    private Long              uid;

    @ApiModelProperty(value = "店铺ID")
    private Long              storeId;

    @ApiModelProperty(value = "店铺名")
    private String            shopName;

    @ApiModelProperty(value = "店铺logo")
    private String            logo;

    @ApiModelProperty(value = "店铺联系方式")
    private String            contactMobile;

    @ApiModelProperty(value = "类型(收藏(collect）、点赞(like))")
    private String            type;

    @ApiModelProperty(value = "添加时间")
    private Timestamp         addTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp         modifyTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean           deleted;

}
