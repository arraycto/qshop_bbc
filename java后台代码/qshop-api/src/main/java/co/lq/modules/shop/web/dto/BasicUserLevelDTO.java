package co.lq.modules.shop.web.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 基础营销会员等级数据
 *
 * @author songbin
 * @since 2020年4月25日 下午2:58:38
 */
@Data
public class BasicUserLevelDTO implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 会员等级编号
     */
    private Long              id;

    /**
     * 会员等级名称
     */
    private String            name;
}
