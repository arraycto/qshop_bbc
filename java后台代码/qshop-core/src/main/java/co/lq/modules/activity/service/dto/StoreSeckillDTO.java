package co.lq.modules.activity.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import co.lq.modules.shop.service.param.StoreProductParam;
import lombok.Data;

/**
 * @author billy
 * @date 2019-12-14
 */
@Data
public class StoreSeckillDTO implements Serializable {

    // 商品秒杀产品表id
    private Long                    id;

    // 商品id
    private Long                    productId;

    // 推荐图
    private String                  image;

    // 轮播图
    private String                  images;

    // 活动标题
    private String                  title;

    // 简介
    private String                  info;

    // 价格
    private BigDecimal              price;

    // 成本
    private BigDecimal              cost;

    // 原价
    private BigDecimal              otPrice;

    // 返多少积分
    private BigDecimal              giveIntegral;

    // 排序
    private Integer                 sort;

    // 库存
    private Integer                 stock;

    // 销量
    private Integer                 sales;

    // 单位名
    private String                  unitName;

    // 邮费
    private BigDecimal              postage;

    // 内容
    private String                  description;

    // 开始时间
    private Integer                 startTime;

    // 结束时间
    private Integer                 stopTime;

    // 添加时间
    private String                  addTime;

    // 产品状态
    private Integer                 status;

    // 是否包邮
    private Integer                 isPostage;

    // 删除 0未删除1已删除
    private Integer                 isDel;

    // 最多秒杀几个
    private Integer                 num;

    // 显示
    private Integer                 isShow;

    private Timestamp               startTimeDate;

    private Timestamp               endTimeDate;

    private String                  statusStr;

    //所属店铺
    private Long                    storeId;

    //审核状态
    private Byte                    verifyStatus;

    private List<StoreProductParam> productList;
}
