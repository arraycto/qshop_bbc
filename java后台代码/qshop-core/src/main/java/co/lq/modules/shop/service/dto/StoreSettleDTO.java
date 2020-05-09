package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-28
 */
@Data
public class StoreSettleDTO implements Serializable {

    /** 店铺入驻信息id */
    /** 防止精度丢失 */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long       id;

    /** 所属店铺 */
    private Long       storeId;

    /** 公司名称 */
    private String     companyName;

    /** 营业执照注册号 */
    private String     licenseNo;

    /** 法定代表人姓名 */
    private String     representative;

    /** 法定代表人身份证号 */
    private String     idNo;

    /** 法定代表人身份证正面 */
    private String     representIdentity1;

    /** 法定代表人身份证反面 */
    private String     representIdentity2;

    /** 营业执照开始时间 */
    private Timestamp  licenseStartTime;

    /** 营业执照结束时间 */
    private Timestamp  licenseEndTime;

    /** 公司所在地 */
    private String     area;

    /** 公司详细地址 */
    private String     addressDetail;

    /** 公司电话 */
    private String     tel;

    /** 公司联系人 */
    private String     contact;

    /** 公司联系人手机 */
    private String     phone;

    /** 注册资本 */
    private BigDecimal capital;

    /** 公司官网 */
    private String     websiteUrl;

    /** 添加时间 */
    private Timestamp  addTime;

    /** 更新时间 */
    private Timestamp  modifyTime;

    /** 逻辑删除 */
    private Integer    deleted;

    /** 1 通过 2 审核中 3 拒绝 */
    private Integer    status;

    /** 0 关闭，1 存续 */
    private Integer    closed;
}
