package co.lq.modules.activity.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import co.lq.modules.shop.service.param.StoreProductParam;
import lombok.Data;

/**
 * @author billy
 * @date 2019-11-18
 */
@Data
public class StoreCombinationDTO implements Serializable {

    private Long                    id;

    // 商品id
    private Long                    productId;

    // 推荐图
    private String                  image;

    // 轮播图
    private String                  images;

    //参与人数
    private Integer                 countPeopleAll;

    //成团人数
    private Integer                 countPeoplePink;

    //访问人数
    private Integer                 countPeopleBrowse;

    // 活动标题
    private String                  title;

    // 活动属性
    private String                  attr;

    // 参团人数
    private Integer                 people;

    // 简介
    private String                  info;

    // 价格
    private BigDecimal              price;

    // 排序
    private Integer                 sort;

    // 销量
    private Integer                 sales;

    // 库存
    private Integer                 stock;

    // 添加时间
    private String                  addTime;

    // 产品状态
    private Integer                 isShow;

    private Integer                 isDel;

    private Integer                 combination;

    // 是否包邮1是0否
    private Integer                 isPostage;

    // 邮费
    private BigDecimal              postage;

    // 拼团内容
    private String                  description;

    // 拼团开始时间
    private Integer                 startTime;

    // 拼团结束时间
    private Integer                 stopTime;

    private Date                    startTimeDate;

    private Date                    endTimeDate;

    // 拼团订单有效时间
    private Integer                 effectiveTime;

    // 拼图产品成本
    private Integer                 cost;

    // 浏览量
    private Integer                 browse;

    // 单位名
    private String                  unitName;

    //所属店铺
    private Long                    storeId;

    //审核状态
    private Byte                    verifyStatus;

    private String                  statusStr;

    // 砍价状态 0(到砍价时间不自动开启)  1(到砍价时间自动开启时间)
    private Integer                 status;

    private List<StoreProductParam> productList;
}
