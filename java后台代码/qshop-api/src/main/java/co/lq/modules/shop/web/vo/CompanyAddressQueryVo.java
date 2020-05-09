package co.lq.modules.shop.web.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 公司收发货地址表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2020-05-05
 */
@Data
@ApiModel(value = "CompanyAddressQueryVo对象", description = "公司收发货地址表查询参数")
public class CompanyAddressQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long              id;

    @ApiModelProperty(value = "地址名称")
    private String            addressName;

    @ApiModelProperty(value = "默认发货地址：0->否；1->是")
    private Integer           sendStatus;

    @ApiModelProperty(value = "是否默认收货地址：0->否；1->是")
    private Integer           receiveStatus;

    @ApiModelProperty(value = "收发货人姓名")
    private String            name;

    @ApiModelProperty(value = "收货人电话")
    private String            phone;

    @ApiModelProperty(value = "省/直辖市")
    private String            province;

    @ApiModelProperty(value = "市")
    private String            city;

    @ApiModelProperty(value = "区")
    private String            region;

    @ApiModelProperty(value = "详细地址")
    private String            detailAddress;

    @ApiModelProperty(value = "所属店铺")
    private Long              storeId;

    @ApiModelProperty(value = "添加时间")
    private Timestamp         addTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp         modifyTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean           deleted;

}
