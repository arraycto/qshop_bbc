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
 * 专题表
 * </p>
 *
 * @author billy
 * @since 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="CmsSubject对象", description="专题表")
public class CmsSubject extends BaseEntity {

    private static final long serialVersionUID = 1L;

@TableId(value = "id", type = IdType.AUTO)
private Long id;

@ApiModelProperty(value = "分类")
private Long categoryId;

@ApiModelProperty(value = "标题")
private String title;

@ApiModelProperty(value = "专题主图")
private String pic;

@ApiModelProperty(value = "关联产品数量")
private Integer productCount;

@ApiModelProperty(value = "推荐")
private Integer recommendStatus;

@ApiModelProperty(value = "收藏量")
private Integer collectCount;

@ApiModelProperty(value = "点击量")
private Integer readCount;

@ApiModelProperty(value = "评论量")
private Integer commentCount;

@ApiModelProperty(value = "画册图片用逗号分割")
private String albumPics;

@ApiModelProperty(value = "描述")
private String description;

@ApiModelProperty(value = "显示状态：0->不显示；1->显示")
private Integer showStatus;

@ApiModelProperty(value = "内容")
private String content;

@ApiModelProperty(value = "转发数")
private Integer forwardCount;

@ApiModelProperty(value = "专题分类名称")
private String categoryName;

private Long memberId;

@ApiModelProperty(value = "打赏")
private Integer reward;

private String areaName;

private String schoolName;

private String memberName;

private String videoSrc;

private Integer type;

@ApiModelProperty(value = "添加时间")
private Date addTime;

@ApiModelProperty(value = "更新时间")
private Date modifyTime;

@ApiModelProperty(value = "逻辑删除")
private Boolean deleted;

}
