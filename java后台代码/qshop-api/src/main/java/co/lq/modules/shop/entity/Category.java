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
 * 商品分类表
 * </p>
 *
 * @author billy
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Category对象", description = "商品分类表")
public class Category extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品分类表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer           id;

    @ApiModelProperty(value = "父id")
    private Integer           pid;

    @ApiModelProperty(value = "分类名称")
    private String            cateName;

    @ApiModelProperty(value = "排序")
    private Integer           sort;

    @ApiModelProperty(value = "图标")
    private String            pic;

    @ApiModelProperty(value = "是否推荐")
    private Boolean           isShow;

    @ApiModelProperty(value = "添加时间")
    private Integer           addTime;

    @ApiModelProperty(value = "修改时间")
    private Integer           modifyTime;

}
