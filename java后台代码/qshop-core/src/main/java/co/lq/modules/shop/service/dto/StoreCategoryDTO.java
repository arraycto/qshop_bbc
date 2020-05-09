package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-10
 */
@Data
public class StoreCategoryDTO implements Serializable {

    /** 商品分类表ID */
    private Long                   id;

    // 父id
    private Long                   pid;

    /** 店铺id */
    private Long                   storeId;

    /** 分类名称 */
    private String                 cateName;

    /** 排序 */
    private Integer                sort;

    /** 图标 */
    private String                 pic;

    /** 是否显示 */
    private Integer                isShow;

    /** 添加时间 */
    private Timestamp              addTime;

    /** 更新时间 */
    private Timestamp              modifyTime;

    /** 逻辑删除 */
    private Integer                deleted;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<StoreCategoryDTO> children;

    public String getLabel() {
        return cateName;
    }
}
