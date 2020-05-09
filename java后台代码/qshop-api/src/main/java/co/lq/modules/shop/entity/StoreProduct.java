package co.lq.modules.shop.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author billy
 * @since 2019-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreProduct对象", description = "商品表")
public class StoreProduct extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "商品图片")
    private String            image;

    @ApiModelProperty(value = "轮播图")
    private String            sliderImage;

    @ApiModelProperty(value = "商品名称")
    private String            productName;

    @ApiModelProperty(value = "商品简介")
    private String            storeInfo;

    @ApiModelProperty(value = "关键字")
    private String            keyword;

    @ApiModelProperty(value = "产品条码（一维码）")
    private String            barCode;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal        price;

    @ApiModelProperty(value = "会员价格")
    private BigDecimal        vipPrice;

    @ApiModelProperty(value = "市场价")
    private BigDecimal        otPrice;

    @ApiModelProperty(value = "邮费")
    private BigDecimal        postage;

    @ApiModelProperty(value = "单位名")
    private String            unitName;

    @ApiModelProperty(value = "排序")
    private Integer           sort;

    @ApiModelProperty(value = "销量")
    private Integer           sales;

    @ApiModelProperty(value = "库存")
    private Integer           stock;

    @ApiModelProperty(value = "状态（0：未上架，1：上架）")
    private Integer           isShow;

    @ApiModelProperty(value = "是否热卖")
    private Integer           isHot;

    @ApiModelProperty(value = "是否优惠")
    private Integer           isBenefit;

    @ApiModelProperty(value = "是否精品")
    private Integer           isBest;

    @ApiModelProperty(value = "是否新品")
    private Integer           isNew;

    @ApiModelProperty(value = "产品描述")
    private String            description;

    @ApiModelProperty(value = "添加时间")
    private Integer           addTime;

    @ApiModelProperty(value = "是否包邮")
    private Integer           isPostage;

    @ApiModelProperty(value = "是否删除")
    private Integer           isDel;

    @ApiModelProperty(value = "商户是否代理 0不可代理1可代理")
    private Integer           merUse;

    @ApiModelProperty(value = "获得积分")
    private BigDecimal        giveIntegral;

    @ApiModelProperty(value = "成本价")
    private BigDecimal        cost;

    @ApiModelProperty(value = "秒杀状态 0 未开启 1已开启")
    private Integer           isSeckill;

    @ApiModelProperty(value = "砍价状态 0未开启 1开启")
    private Integer           isBargain;

    @ApiModelProperty(value = "是否优品推荐")
    private Integer           isGood;

    @ApiModelProperty(value = "虚拟销量")
    private Integer           ficti;

    @ApiModelProperty(value = "浏览量")
    private Integer           browse;

    @ApiModelProperty(value = "产品二维码地址(用户小程序海报)")
    private String            codePath;

    @ApiModelProperty(value = "淘宝京东1688类型")
    private String            soureLink;

    @ApiModelProperty(name = "店铺id")
    private Integer           storeId;

    @ApiModelProperty(name = "平台类目")
    private Long              catalogId;

    @ApiModelProperty(name = "商品重量")
    private BigDecimal        weight;

    @ApiModelProperty(name = "运费模板id")
    private Long              feightTemplateId;
}
