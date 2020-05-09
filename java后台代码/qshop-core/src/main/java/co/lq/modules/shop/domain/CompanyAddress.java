package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-27
 */
@Entity
@Data
@Table(name = "company_address")
public class CompanyAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long      id;

    /** 地址名称 */
    @Column(name = "address_name")
    private String    addressName;

    /** 默认发货地址：0->否；1->是 */
    @Column(name = "send_status")
    private Integer   sendStatus;

    /** 是否默认收货地址：0->否；1->是 */
    @Column(name = "receive_status")
    private Integer   receiveStatus;

    /** 收发货人姓名 */
    @Column(name = "name")
    private String    name;

    /** 收货人电话 */
    @Column(name = "phone")
    private String    phone;

    /** 省/直辖市 */
    @Column(name = "province")
    private String    province;

    /** 市 */
    @Column(name = "city")
    private String    city;

    /** 区 */
    @Column(name = "region")
    private String    region;

    /** 详细地址 */
    @Column(name = "detail_address")
    private String    detailAddress;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Long      storeId;

    /** 添加时间 */
    @Column(name = "add_time")
    private Timestamp addTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Timestamp modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted")
    private Integer   deleted;

    public void copy(CompanyAddress source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
