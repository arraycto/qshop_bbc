package co.lq.modules.shop.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-11
 */
@Data
public class StoreSystemConfigDTO implements Serializable {

    /** 配置id */
    private Long    id;

    /** 字段名称 */
    private String  menuName;

    /** 默认值 */
    private String  value;

    /** 排序 */
    private Integer sort;

    /** 是否隐藏 */
    private Integer status;

    /** 所属店铺 */
    private Long    storeId;
}
