package co.lq.modules.shop.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 店铺入驻信息
 *
 * @author songbin
 * @since 2020年3月31日 下午2:00:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreCart对象", description = "购物车表")
public class StoreSettle extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 店铺入驻信息id */
    @ApiModelProperty(value = "店铺入驻信息id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    /** 所属店铺 */
    @ApiModelProperty(value = "所属店铺")
    private Long              storeId;

    /** 公司名称 */
    @ApiModelProperty(value = "公司名称")
    private String            companyName;

    /** 营业执照注册号 */
    @ApiModelProperty(value = "营业执照注册号")
    private String            licenseNo;

    /** 法定代表人姓名 */
    @ApiModelProperty(value = "法定代表人姓名")
    private String            representative;

    /** 法定代表人身份证号 */
    @ApiModelProperty(value = "法定代表人身份证号")
    private String            idNo;

    /** 法定代表人身份证正面 */
    @ApiModelProperty(value = "法定代表人身份证正面")
    private String            representIdentity1;

    /** 法定代表人身份证反面 */
    @ApiModelProperty(value = "法定代表人身份证反面")
    private String            representIdentity2;

    /** 营业执照开始时间 */
    @ApiModelProperty(value = "营业执照开始时间")
    private Timestamp         licenseStartTime;

    /** 营业执照结束时间 */
    @ApiModelProperty(value = "营业执照结束时间")
    private Timestamp         licenseEndTime;

    /** 公司所在地 */
    @ApiModelProperty(value = "公司所在地")
    private String            area;

    /** 公司详细地址 */
    @ApiModelProperty(value = "公司详细地址")
    private String            addressDetail;

    /** 公司电话 */
    @ApiModelProperty(value = "公司电话")
    private String            tel;

    /** 公司联系电话 */
    @ApiModelProperty(value = "公司联系人")
    private String            contact;

    /** 公司联系人手机 */
    @ApiModelProperty(value = "公司联系人手机")
    private String            phone;

    /** 注册资本 */
    @ApiModelProperty(value = "注册资本")
    private BigDecimal        capital;

    /** 公司官网 */
    @ApiModelProperty(value = "公司官网")
    private String            websiteUrl;

    /** 添加时间 */
    @ApiModelProperty(value = "添加时间")
    private Timestamp         addTime;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Timestamp         modifyTime;

    /** 逻辑删除 */
    @ApiModelProperty(value = "逻辑删除")
    private Integer           deleted;

    /** 1 通过 2 审核中 3 拒绝 */
    @ApiModelProperty(value = "审核状态")
    private Integer           status;

    /** 0 关闭，1 存续 */
    @ApiModelProperty(value = "关闭与否")
    private Integer           closed;

    @ApiModelProperty(value = "卖家用户id")
    @TableField(exist = false)
    private Long              uid;
}
