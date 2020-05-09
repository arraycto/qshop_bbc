package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-28
 */
@Entity
@Data
@Table(name = "store_settle")
public class StoreSettle implements Serializable {

    /** 店铺入驻信息id */
    @Id
    @Column(name = "id")
    private Long       id;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Long       storeId;

    /** 公司名称 */
    @Column(name = "company_name")
    private String     companyName;

    /** 营业执照注册号 */
    @Column(name = "license_no")
    private String     licenseNo;

    /** 法定代表人姓名 */
    @Column(name = "representative")
    private String     representative;

    /** 法定代表人身份证号 */
    @Column(name = "id_no")
    private String     idNo;

    /** 法定代表人身份证正面 */
    @Column(name = "represent_identity1")
    private String     representIdentity1;

    /** 法定代表人身份证反面 */
    @Column(name = "represent_identity2")
    private String     representIdentity2;

    /** 营业执照开始时间 */
    @Column(name = "license_start_time", nullable = false)
    @NotNull
    private Timestamp  licenseStartTime;

    /** 营业执照结束时间 */
    @Column(name = "license_end_time", nullable = false)
    @NotNull
    private Timestamp  licenseEndTime;

    /** 公司所在地 */
    @Column(name = "area")
    private String     area;

    /** 公司详细地址 */
    @Column(name = "address_detail")
    private String     addressDetail;

    /** 公司电话 */
    @Column(name = "tel")
    private String     tel;

    /** 公司联系电话 */
    @Column(name = "contact")
    private String     contact;

    /** 公司联系人手机 */
    @Column(name = "phone")
    private String     phone;

    /** 注册资本 */
    @Column(name = "capital")
    private BigDecimal capital;

    /** 公司官网 */
    @Column(name = "website_url")
    private String     websiteUrl;

    /** 添加时间 */
    @Column(name = "add_time", nullable = false)
    private Timestamp  addTime;

    /** 更新时间 */
    @Column(name = "modify_time", nullable = false)
    private Timestamp  modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted", nullable = false)
    private Integer    deleted;

    /** 1 通过 2 审核中 3 拒绝 */
    @Column(name = "status")
    private Integer    status;

    /** 0 关闭，1 存续 */
    @Column(name = "closed")
    private Integer    closed;

    public void copy(StoreSettle source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
