package co.lq.modules.activity.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author billy
 * @date 2019-11-18
 */
@Data
public class StoreVisitDTO implements Serializable {

    private Long    id;

    // 产品ID
    private Long    productId;

    // 产品类型
    private String  productType;

    // 产品分类ID
    private Long    cateId;

    // 产品类型
    private String  type;

    // 用户ID
    private Long    uid;

    // 访问次数
    private Integer count;

    // 备注描述
    private String  content;

    // 添加时间
    private Integer addTime;

    //所属店铺
    private Long    storeId;
}
