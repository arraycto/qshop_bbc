package co.lq.modules.shop.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 处理规则类
 *
 * @author songbin
 * @since 2020年4月2日 下午6:13:34
 */
@Data
public class BasicRuls implements Serializable {
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;

}
