package co.lq.modules.shop.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 首页推荐商品表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2020-04-18
 */
@Data
@ApiModel(value = "HomeNewProductQueryVo对象", description = "首页推荐商品表查询参数")
public class HomeNewProductQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long              id;

    private Long              productId;

    private String            productName;

    private BigDecimal        productPrice;

    private String            image;

    private Integer           recommendStatus;

    private BigDecimal        originalPrie;

    private Integer           sales;

    private Integer           sort;

    @ApiModelProperty(value = "添加时间")
    private Date              addTime;

    @ApiModelProperty(value = "更新时间")
    private Date              modifyTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean           deleted;

    @ApiModelProperty(value = "所属店铺")
    private Long              storeId;

}
