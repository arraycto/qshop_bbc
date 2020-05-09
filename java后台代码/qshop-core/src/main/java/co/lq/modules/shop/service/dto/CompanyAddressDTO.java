package co.lq.modules.shop.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Data
public class CompanyAddressDTO implements Serializable {

    private Long id;

    /** 地址名称 */
    private String addressName;

    /** 默认发货地址：0->否；1->是 */
    private Integer sendStatus;

    /** 是否默认收货地址：0->否；1->是 */
    private Integer receiveStatus;

    /** 收发货人姓名 */
    private String name;

    /** 收货人电话 */
    private String phone;

    /** 省/直辖市 */
    private String province;

    /** 市 */
    private String city;

    /** 区 */
    private String region;

    /** 详细地址 */
    private String detailAddress;

    /** 所属店铺 */
    private Long storeId;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer deleted;
}