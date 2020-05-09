package co.lq.modules.shop.service.param;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * TODO 类实现描述
 *
 * @author songbin
 * @since 2020年4月16日 下午5:14:38
 */
@Data
public class ExpressInfoParam implements Serializable {

    private String       LogisticCode;

    private String       ShipperCode;

    private List<Traces> Traces;

    private String       State;

    private String       EBusinessID;

    private boolean      Success;

    private String       Reason;

    private String       ShipperName;

    private String       OrderCode;
}
