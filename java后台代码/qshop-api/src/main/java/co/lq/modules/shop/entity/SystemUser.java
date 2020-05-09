package co.lq.modules.shop.entity;

import java.sql.Timestamp;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 店铺卖家帐号
 *
 * @author songbin
 * @since 2020年3月31日 下午2:57:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SellerUser对象", description = "平台店铺表")
public class SystemUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "平台类目表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "用户帐号")
    private String            username;

    /** 用户昵称 */
    @ApiModelProperty(value = "用户昵称")
    private String            nickName;

    /** 性别 */
    @ApiModelProperty(value = "性别")
    private String            sex;

    @ApiModelProperty(value = "用户头像")
    private Long              avatarId;

    @ApiModelProperty(value = "邮箱")
    private String            email;

    @ApiModelProperty(value = "电话")
    private String            phone;

    @ApiModelProperty(value = "是否启用")
    private Boolean           enabled;

    @ApiModelProperty(value = "密码")
    private String            password;

    @ApiModelProperty(value = "添加时间")
    private Timestamp         createTime;

    @ApiModelProperty(value = "最近一次密码重置时间")
    private Date              lastPasswordResetTime;

    @ApiModelProperty(value = "职位id")
    private Long              jobId;

    @ApiModelProperty(value = "职位id")
    private Long              deptId;

    // 所属门店
    @ApiModelProperty(value = "所属门店")
    private Long              storeId;
}
