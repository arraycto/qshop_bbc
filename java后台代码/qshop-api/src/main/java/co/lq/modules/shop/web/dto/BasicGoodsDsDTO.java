package co.lq.modules.shop.web.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 基础营销商品相关（包括店铺分类）
 *
 * @author songbin
 * @since 2020年4月25日 下午3:31:39
 */
@Data
public class BasicGoodsDsDTO implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 分类或者商品编号
     */
    private Long              id;

    /**
     * 二级分类名称或者商品名称
     */
    private String            name;

    /**
     * 一级分类名称或者商品编码
     */
    private String            name1;
}
