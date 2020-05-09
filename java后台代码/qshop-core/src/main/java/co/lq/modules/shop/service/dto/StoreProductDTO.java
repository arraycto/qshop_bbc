package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import co.lq.modules.shop.service.param.MemberPriceParam;
import co.lq.modules.shop.service.param.PrefrenceAreaProductRelationParam;
import co.lq.modules.shop.service.param.ProductAttributeValueParam;
import co.lq.modules.shop.service.param.ProductFullReductionParam;
import co.lq.modules.shop.service.param.ProductLadderParam;
import co.lq.modules.shop.service.param.SkuStockParam;
import co.lq.modules.shop.service.param.SubjectProductRelationParam;
import lombok.Data;

/**
 * @author billy
 * @date 2019-10-04
 */
@Data
public class StoreProductDTO implements Serializable {

    // 商品id
    private Long                                    id;

    // 商品图片
    private String                                  image;

    // 轮播图
    private String                                  sliderImage;

    // 商品名称
    private String                                  productName;

    // 商品简介
    private String                                  storeInfo;

    // 关键字
    private String                                  keyword;

    // 产品条码（一维码）
    private String                                  barCode;

    private Long                                    storeCateId;

    private String                                  storeCateName;

    private Long                                    brandId;

    private String                                  brandName;

    // 商品价格
    private BigDecimal                              price;

    // 会员价格
    private BigDecimal                              vipPrice;

    // 市场价
    private BigDecimal                              otPrice;

    // 邮费
    private BigDecimal                              postage;

    // 单位名
    private String                                  unitName;

    // 排序
    private Integer                                 sort;

    // 销量
    private Integer                                 sales;

    // 库存
    private Integer                                 stock;

    // 状态（0：未上架，1：上架）
    private Integer                                 isShow;

    // 是否热卖
    private Integer                                 isHot;

    // 是否优惠
    private Integer                                 isBenefit;

    // 是否精品
    private Integer                                 isBest;

    // 是否新品
    private Integer                                 isNew;

    // 产品描述
    private String                                  description;

    // 添加时间
    private Integer                                 addTime;

    // 是否包邮
    private Integer                                 isPostage;

    // 是否删除
    private Integer                                 isDel;

    // 商户是否代理 0不可代理1可代理
    private Integer                                 merUse;

    // 获得积分
    private BigDecimal                              giveIntegral;

    // 成本价
    private BigDecimal                              cost;

    // 秒杀状态 0 未开启 1已开启
    private Integer                                 isSeckill;

    // 砍价状态 0未开启 1开启
    private Integer                                 isBargain;

    // 是否优品推荐
    private Integer                                 isGood;

    // 虚拟销量
    private Integer                                 ficti;

    // 浏览量
    private Integer                                 browse;

    // 产品二维码地址(用户小程序海报)
    private String                                  codePath;

    // 淘宝京东1688类型
    private String                                  soureLink;

    /** 店铺id */
    private Long                                    storeId;

    // 审核状态
    private Integer                                 verifyStatus;

    // 商品类目id
    private Long                                    catalogId;

    // 商品重量
    private BigDecimal                              weight;

    //运费模板id
    private Long                                    feightTemplateId;

    private List<Long>                              categoryList;

    private List<Long>                              storeCategoryList;

    private List<String>                            galleryList;

    /**
     * 商品会员价格
     */
    private List<MemberPriceParam>                  memberPriceList;

    /**
     * 商品优惠方案
     */
    private List<ProductFullReductionParam>         productFullReductionList;

    /**
     * 商品阶梯价格
     */
    private List<ProductLadderParam>                productLadderList;

    /**
     * 商品属性列表
     */
    private List<ProductAttributeValueParam>        productAttributeValueList;

    /**
     * 商品shu值列表
     */
    private List<SkuStockParam>                     skuStockList;

    /**
     * 关联商品列表
     */
    private List<SubjectProductRelationParam>       subjectProductRelationList;

    /**
     * 商品地区列表
     */
    private List<PrefrenceAreaProductRelationParam> prefrenceAreaProductRelationList;
}
