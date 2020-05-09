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
 * 店铺收藏表
 * </p>
 *
 * @author billy
 * @since 2020-04-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreCollect对象", description = "店铺收藏表")
public class StoreCollect extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品点赞ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "用户ID")
    private Long              uid;

    @ApiModelProperty(value = "店铺ID")
    private Long              storeId;

    @ApiModelProperty(value = "类型(收藏(collect）、点赞(like))")
    private String            type;

    @ApiModelProperty(value = "添加时间")
    private Timestamp         addTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp         modifyTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean           deleted;

}
