package co.lq.modules.wechat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 微信模板
 * </p>
 *
 * @author billy
 * @since 2019-12-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WechatTemplate对象", description = "微信模板")
public class WechatTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer           id;

    @ApiModelProperty(value = "模板编号")
    private String            tempkey;

    @ApiModelProperty(value = "模板名")
    private String            name;

    @ApiModelProperty(value = "回复内容")
    private String            content;

    @ApiModelProperty(value = "模板ID")
    private String            tempid;

    @ApiModelProperty(value = "添加时间")
    private String            addTime;

    @ApiModelProperty(value = "状态")
    private Integer           status;

}
