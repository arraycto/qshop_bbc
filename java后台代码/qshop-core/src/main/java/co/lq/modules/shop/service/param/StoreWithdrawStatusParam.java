package co.lq.modules.shop.service.param;

import java.io.Serializable;

import lombok.Data;

/**
 * 提现审核状态参数
 *
 * @author songbin
 * @since 2020年4月13日 下午11:44:35
 */
@Data
public class StoreWithdrawStatusParam implements Serializable {
    private static final long serialVersionUID = 1;

    private Long              id;

    private Integer           applyStatus;

    private String            detail;

    private Long              storeId;
}
