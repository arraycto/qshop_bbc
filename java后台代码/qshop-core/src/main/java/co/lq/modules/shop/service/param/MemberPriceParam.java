package co.lq.modules.shop.service.param;

import java.io.Serializable;

import lombok.Data;

/**
 * 商品详情会员价格对象
 *
 * @author songbin
 * @since 2020年4月10日 下午4:35:03
 */
@Data
public class MemberPriceParam implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 会员等级id
     */
    private Long              memberLevelId;

    /**
     * 会员等级名称
     */
    private String            memberLevelName;
}
