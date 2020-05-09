package co.lq.modules.activity.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import co.lq.modules.shop.service.param.StoreProductParam;
import lombok.Data;

/**
 * @author billy
 * @date 2019-11-18
 */
@Entity
@Data
@Table(name = "store_combination")
public class StoreCombination implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long                    id;

    // 商品id
    @Column(name = "product_id", nullable = false)
    private Long                    productId;

    // 推荐图
    @Column(name = "image", nullable = false)
    @NotBlank(message = "请上传产品图片")
    private String                  image;

    // 轮播图
    @Column(name = "images", nullable = false)
    @NotBlank(message = "请上传产品轮播图")
    private String                  images;

    // 活动标题
    @Column(name = "title", nullable = false)
    @NotBlank(message = "请输入拼团名称")
    private String                  title;

    // 活动属性
    @Column(name = "attr")
    private String                  attr;

    // 拼团状态 0(到拼团时间不自动开启)  1(到拼团时间自动开启时间)
    @Column(name = "status")
    private Integer                 status;

    // 参团人数
    @Column(name = "people", nullable = false)
    @NotNull(message = "拼团人数必填")
    @Min(value = 2, message = "拼团人数必须大于1")
    private Integer                 people;

    // 简介
    @Column(name = "info", nullable = false)
    @NotBlank(message = "请输入拼团简介")
    private String                  info;

    // 价格
    @Column(name = "price", nullable = false)
    @NotNull(message = "拼团价必填")
    @Min(value = 0, message = "拼团价必须大于0")
    private BigDecimal              price;

    // 排序
    @Column(name = "sort", nullable = false)
    @NotNull(message = "排序必填")
    private Integer                 sort;

    // 销量
    @Column(name = "sales", nullable = false)
    private Integer                 sales;

    // 库存
    @Column(name = "stock", nullable = false)
    @NotNull(message = "库存必填")
    private Integer                 stock;

    // 添加时间
    @Column(name = "add_time", nullable = false)
    private String                  addTime;

    // 产品状态
    @Column(name = "is_show", nullable = false)
    @NotNull(message = "状态必须选择")
    private Integer                 isShow;

    @Column(name = "is_del", nullable = false, insertable = false)
    private Integer                 isDel;

    @Column(name = "combination", nullable = false, insertable = false)
    private Integer                 combination;

    // 是否包邮1是0否
    @Column(name = "is_postage", nullable = false)
    @NotNull(message = "包邮状态必须选择")
    private Integer                 isPostage;

    // 邮费
    @Column(name = "postage", nullable = false)
    private BigDecimal              postage;

    // 拼团内容
    @Column(name = "description", nullable = false)
    @NotBlank(message = "拼团内容不能为空")
    private String                  description;

    // 拼团开始时间
    @Column(name = "start_time", nullable = false)
    private Integer                 startTime;

    // 拼团结束时间
    @Column(name = "stop_time", nullable = false)
    private Integer                 stopTime;

    @NotNull(message = "开始时间不能为空")
    private Date                    startTimeDate;

    @NotNull(message = "结束时间不能为空")
    private Date                    endTimeDate;

    // 拼团订单有效时间
    @Column(name = "effective_time", nullable = false)
    private Integer                 effectiveTime;

    // 拼图产品成本
    @Column(name = "cost", nullable = false)
    private Integer                 cost;

    // 浏览量
    @Column(name = "browse")
    private Integer                 browse;

    // 单位名
    @Column(name = "unit_name", nullable = false)
    private String                  unitName;

    // 店铺id
    @Column(name = "store_id", nullable = false)
    private Long                    storeId;

    // 审核状态
    @Column(name = "verify_status", nullable = false)
    private Integer                 verifyStatus;

    @Transient
    private List<StoreProductParam> productList;

    public void copy(StoreCombination source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
