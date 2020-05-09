package co.lq.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 首页推荐商品表
 * </p>
 *
 * @author billy
 * @since 2020-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="HomeNewProduct对象", description="首页推荐商品表")
public class HomeNewProduct extends BaseEntity {

    private static final long serialVersionUID = 1L;

@TableId(value = "id", type = IdType.AUTO)
private Long id;

private Long productId;

private String productName;

private Integer recommendStatus;

private Integer sort;

@ApiModelProperty(value = "添加时间")
private Date addTime;

@ApiModelProperty(value = "更新时间")
private Date modifyTime;

@ApiModelProperty(value = "逻辑删除")
private Boolean deleted;

@ApiModelProperty(value = "所属店铺")
private Long storeId;

}
