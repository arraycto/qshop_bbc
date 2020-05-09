package co.lq.modules.activity.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
 * @date 2019-12-14
 */
@Entity
@Data
@Table(name = "store_seckill")
public class StoreSeckill implements Serializable {

    // 商品秒杀产品表id
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
    @NotBlank(message = "请输入产品标题")
    private String                  title;

    // 简介
    @Column(name = "info", nullable = false)
    @NotBlank(message = "请输入秒杀简介")
    private String                  info;

    // 价格
    @Column(name = "price", nullable = false)
    @NotNull(message = "秒杀价必填")
    @Min(value = 0, message = "秒杀价必须大于0")
    private BigDecimal              price;

    // 成本
    @Column(name = "cost", nullable = false)
    @NotNull(message = "成本价必填")
    @Min(value = 0, message = "成本价必须大于0")
    private BigDecimal              cost;

    // 原价
    @Column(name = "ot_price", nullable = false)
    @NotNull(message = "原价必填")
    @Min(value = 0, message = "原价必须大于0")
    private BigDecimal              otPrice;

    // 返多少积分
    @Column(name = "give_integral")
    private BigDecimal              giveIntegral;

    // 排序
    @Column(name = "sort", nullable = false)
    @NotNull(message = "排序必填")
    private Integer                 sort;

    // 库存
    @Column(name = "stock", nullable = false)
    @NotNull(message = "库存必填")
    private Integer                 stock;

    // 销量
    @Column(name = "sales", nullable = false)
    private Integer                 sales;

    // 单位名
    @Column(name = "unit_name")
    private String                  unitName;

    // 邮费
    @Column(name = "postage", nullable = false)
    private BigDecimal              postage;

    // 内容
    @Column(name = "description")
    private String                  description;

    // 开始时间
    @Column(name = "start_time", nullable = false)
    private Integer                 startTime;

    // 结束时间
    @Column(name = "stop_time", nullable = false)
    private Integer                 stopTime;

    @Column(name = "start_time_date")
    private Timestamp               startTimeDate;

    @Column(name = "end_time_date")
    private Timestamp               endTimeDate;

    // 添加时间
    @Column(name = "add_time", nullable = false)
    private String                  addTime;

    // 产品状态
    @Column(name = "status")
    private Integer                 status;

    // 是否包邮
    @Column(name = "is_postage")
    private Integer                 isPostage;

    // 删除 0未删除1已删除
    @Column(name = "is_del")
    private Integer                 isDel;

    // 最多秒杀几个
    @Column(name = "num", nullable = false)
    @NotNull(message = "限购必填")
    @Min(value = 1, message = "限购必须大于0")
    private Integer                 num;

    // 显示
    @Column(name = "is_show")
    private Integer                 isShow;

    // 店铺id
    @Column(name = "store_id")
    private Long                    storeId;

    // 审核状态
    @Column(name = "verify_status")
    private Integer                 verifyStatus;

    @Transient
    private List<StoreProductParam> productList;

    public void copy(StoreSeckill source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
