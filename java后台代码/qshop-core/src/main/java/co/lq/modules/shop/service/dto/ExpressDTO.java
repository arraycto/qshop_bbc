package co.lq.modules.shop.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author billy
 * @date 2019-12-12
 */
@Data
public class ExpressDTO implements Serializable {

    // 快递公司id
    private Integer id;

    // 快递公司简称
    private String  code;

    // 快递公司全称
    private String  name;

    // 排序
    private Integer sort;

    // 是否显示
    private Integer isShow;
}
