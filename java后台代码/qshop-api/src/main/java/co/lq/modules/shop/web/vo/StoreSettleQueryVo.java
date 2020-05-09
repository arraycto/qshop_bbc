package co.lq.modules.shop.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询入驻信息结果
 *
 * @author songbin
 * @since 2020年3月31日 下午2:07:23
 */
@Data
@ApiModel(value = "StoreSettleQueryVo对象", description = "入驻信息表查询参数")
public class StoreSettleQueryVo implements Serializable {

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

    /** 1 通过 2 审核中 3 拒绝 */
    @ApiModelProperty(value = "审核状态")
    private Integer           status;

    /** 0 关闭，1 存续 */
    @ApiModelProperty(value = "关闭与否")
    private Integer           closed;

}
