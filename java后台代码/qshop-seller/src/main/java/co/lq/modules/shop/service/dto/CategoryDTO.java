package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @author billy
 * @date 2019-10-03
 */
@Data
public class CategoryDTO implements Serializable {

    // 商品分类表ID
    private Long              id;

    // 父id
    private Long              pid;

    // 分类名称
    private String            cateName;

    // 排序
    private Integer           sort;

    // 图标
    private String            pic;

    // 是否推荐
    private Integer           isShow;

    // 添加时间
    private Integer           addTime;

    // 类目id
    private Long              catalogId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryDTO> children;

    public String getLabel() {
        return cateName;
    }
}
