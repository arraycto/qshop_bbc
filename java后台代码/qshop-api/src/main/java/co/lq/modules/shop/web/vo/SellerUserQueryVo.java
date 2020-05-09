package co.lq.modules.shop.web.vo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 卖家帐号
 *
 * @author songbin
 * @since 2020年3月31日 下午3:13:45
 */
@Data
@ApiModel(value = "SellerUserQueryVo对象", description = "卖家帐号表查询参数")
public class SellerUserQueryVo implements Serializable {
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

    @ApiModelProperty(value = "职位id")
    private Long              jobId;

    @ApiModelProperty(value = "职位id")
    private Long              deptId;

    // 所属门店
    @ApiModelProperty(value = "所属门店")
    private Long              storeId;

    // 密码
    @ApiModelProperty(value = "密码")
    private String            password;
}
