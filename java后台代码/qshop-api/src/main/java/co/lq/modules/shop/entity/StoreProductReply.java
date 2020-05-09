package co.lq.modules.shop.entity;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreProductReply对象", description = "评论表")
public class StoreProductReply extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "用户ID")
    private Long              uid;

    @ApiModelProperty(value = "订单ID")
    private Long              oid;

    @ApiModelProperty(value = "唯一id")
    @TableField(value = "`unique`")
    @NotBlank(message = "参数错误")
    private String            unique;

    @ApiModelProperty(value = "产品id")
    private Long              productId;

    @ApiModelProperty(value = "某种商品类型(普通商品、秒杀商品）")
    private String            replyType;

    @ApiModelProperty(value = "商品分数")
    private Integer           productScore;

    @ApiModelProperty(value = "服务分数")
    private Integer           serviceScore;

    @ApiModelProperty(value = "评论内容")
    private String            comment;

    @ApiModelProperty(value = "评论图片")
    private String            pics;

    @ApiModelProperty(value = "评论时间")
    private Integer           addTime;

    @ApiModelProperty(value = "管理员回复内容")
    private String            merchantReplyContent;

    @ApiModelProperty(value = "管理员回复时间")
    private Integer           merchantReplyTime;

    @ApiModelProperty(value = "0未删除1已删除")
    private Integer           isDel;

    @ApiModelProperty(value = "0未回复1已回复")
    private Integer           isReply;

}
