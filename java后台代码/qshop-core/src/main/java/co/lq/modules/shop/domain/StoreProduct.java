package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
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
@Entity
@Data
@Table(name = "store_product")
public class StoreProduct implements Serializable {

    // 商品id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long                                    id;

    // 商品图片
    @Column(name = "image", nullable = false)
    @NotBlank(message = "请上传商品图片")
    private String                                  image;

    // 轮播图
    @Column(name = "slider_image")
    @NotBlank(message = "请上传轮播图")
    private String                                  sliderImage;

    // 商品名称
    @Column(name = "product_name", nullable = false)
    @NotBlank(message = "请填写商品名称")
    private String                                  productName;

    // 商品简介
    @Column(name = "store_info")
    private String                                  storeInfo;

    // 关键字
    @Column(name = "keyword")
    private String                                  keyword;

    // 产品条码（一维码）
    @Column(name = "bar_code")
    private String                                  barCode;

    @Column(name = "store_cate_id", nullable = false)
    private Long                                    storeCateId;

    // 分类id
    @OneToOne
    @JoinColumn(name = "store_cate_id", insertable = false, updatable = false)
    private StoreCategory                           storeCategory;

    @Column(name = "brand_id", nullable = false)
    private Long                                    brandId;

    /** 所属店铺 */
    @OneToOne
    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
    private Brand                                   brand;

    // 商品价格
    @Column(name = "price", nullable = false)
    @NotNull(message = "价格必填")
    @Min(value = 0)
    private BigDecimal                              price;

    // 会员价格
    @Column(name = "vip_price", insertable = false)
    private BigDecimal                              vipPrice;

    // 市场价
    @Column(name = "ot_price", nullable = false)
    @Min(value = 0)
    private BigDecimal                              otPrice;

    // 邮费
    @Column(name = "postage", nullable = false)
    @Min(value = 0)
    private BigDecimal                              postage;

    // 单位名
    @Column(name = "unit_name", nullable = false)
    private String                                  unitName;

    // 排序
    @Column(name = "sort", nullable = false)
    @Min(value = 0)
    private Integer                                 sort;

    // 销量
    @Column(name = "sales", nullable = false)
    @Min(value = 0)
    private Integer                                 sales;

    // 库存
    @Column(name = "stock", nullable = false)
    @NotNull(message = "库存必填")
    @Min(value = 0)
    private Integer                                 stock;

    // 状态（0：未上架，1：上架）
    @Column(name = "is_show", insertable = false)
    //@NotNull(message = "状态必须选择")
    private Integer                                 isShow;

    // 是否热卖
    @Column(name = "is_hot", columnDefinition = "int default 0")
    private Integer                                 isHot;

    // 是否优惠
    @Column(name = "is_benefit", columnDefinition = "int default 0")
    private Integer                                 isBenefit;

    // 是否精品
    @Column(name = "is_best", columnDefinition = "int default 0")
    private Integer                                 isBest;

    // 是否新品
    @Column(name = "is_new", columnDefinition = "int default 0")
    private Integer                                 isNew;

    // 产品描述
    @Column(name = "description", nullable = false)
    @NotBlank(message = "产品描述")
    private String                                  description;

    // 添加时间
    @Column(name = "add_time", nullable = false)
    private Integer                                 addTime;

    // 是否包邮
    @Column(name = "is_postage")
    @NotNull(message = "包邮状态必须选择")
    private Integer                                 isPostage;

    // 是否删除
    @Column(name = "is_del", insertable = false)
    private Integer                                 isDel;

    // 商户是否代理 0不可代理1可代理
    @Column(name = "mer_use", nullable = false)
    private Integer                                 merUse;

    // 获得积分
    @Column(name = "give_integral")
    @Min(value = 0)
    private BigDecimal                              giveIntegral;

    // 成本价
    @Column(name = "cost")
    @Min(value = 0)
    private BigDecimal                              cost;

    /**
     * 运费模版id
     */
    @Column(name = "feight_template_id")
    private Long                                    feightTemplateId;

    // 秒杀状态 0 未开启 1已开启
    @Column(name = "is_seckill", columnDefinition = "int default 0")
    private Integer                                 isSeckill;

    // 砍价状态 0未开启 1开启
    @Column(name = "is_bargain", columnDefinition = "int default 0")
    private Integer                                 isBargain;

    // 是否优品推荐
    @Column(name = "is_good", columnDefinition = "int default 0")
    private Integer                                 isGood;

    // 虚拟销量
    @Column(name = "ficti", columnDefinition = "int default 0")
    private Integer                                 ficti;

    // 浏览量
    @Column(name = "browse", columnDefinition = "int default 0")
    private Integer                                 browse;

    // 产品二维码地址(用户小程序海报)
    @Column(name = "code_path", nullable = false)
    private String                                  codePath;

    // 淘宝京东1688类型
    @Column(name = "soure_link")
    private String                                  soureLink;

    // 店铺id
    @Column(name = "store_id", nullable = false)
    private Long                                    storeId;

    // 审核状态
    @Column(name = "verify_status")
    private Integer                                 verifyStatus;

    // 商品类目id
    @Column(name = "catalog_id")
    private Long                                    catalogId;

    @Column(name = "weight")
    private BigDecimal                              weight;

    /**
     * 商品会员价格
     */
    @Transient
    private List<MemberPriceParam>                  memberPriceList;

    /**
     * 商品优惠方案
     */
    @Transient
    private List<ProductFullReductionParam>         productFullReductionList;

    /**
     * 商品所在区域
     */
    @Transient
    private List<ProductLadderParam>                productLadderList;

    /**
     * 商品属性列表
     */
    @Transient
    private List<ProductAttributeValueParam>        productAttributeValueList;

    /**
     * 商品shu值列表
     */
    @Transient
    private List<SkuStockParam>                     skuStockList;

    /**
     * 关联商品列表
     */
    @Transient
    private List<SubjectProductRelationParam>       subjectProductRelationList;

    /**
     * 专题列表
     */
    @Transient
    private List<CmsSubject>                        subjectList;

    /**
     * 商品地区列表
     */
    @Transient
    private List<PrefrenceAreaProductRelationParam> prefrenceAreaProductRelationList;

    public void copy(StoreProduct source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
