package co.lq.modules.shop.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 平台类目
 *
 * @author songbin
 * @since 2020年3月11日 下午5:28:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Catalog对象", description = "平台类目表")
public class Catalog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "平台类目表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer           id;

    @ApiModelProperty(value = "父id")
    private Integer           pid;

    @ApiModelProperty(value = "类目名称")
    private String            name;

    @ApiModelProperty(value = "关键字")
    private String            keywords;

    @ApiModelProperty(value = "类目广告语介绍")
    private String            descs;

    @ApiModelProperty(value = "类目图标")
    private String            iconUrl;

    @ApiModelProperty(value = "类目图片")
    private String            picUrl;

    @ApiModelProperty(value = "类目图片")
    private Integer           level;

    @ApiModelProperty(value = "排序")
    private Integer           sortOrder;

    @ApiModelProperty(value = "创建时间")
    private Date              createTime;

    @ApiModelProperty(value = "修改时间")
    private Date              modifyTime;

    @ApiModelProperty(value = "逻辑删除")
    private Integer           deleted;

    @ApiModelProperty(value = "服务费率")
    private BigDecimal        chargeRate;
}
