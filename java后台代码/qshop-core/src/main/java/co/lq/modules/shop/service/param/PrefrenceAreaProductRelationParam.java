package co.lq.modules.shop.service.param;

import java.io.Serializable;

import lombok.Data;

/**
 * 商品区域销售
 *
 * @author songbin
 * @since 2020年4月10日 下午4:54:15
 */
@Data
public class PrefrenceAreaProductRelationParam implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 区域id
     */
    private Long              prefrenceAreaId;
}
