package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.hutool.core.util.StrUtil;
import co.lq.modules.shop.domain.StoreProductAttrValue;
import lombok.Data;

/**
 * 商品表 查询结果对象
 *
 * @author songbin
 * @since 2020年4月7日 下午8:05:05
 */
@Data
public class StoreProductQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long              id;

    private String            image;

    private String            imageBase;

    private String            codeBase;

    public String getImageBase() {
        return image;
    }

    private Boolean      userCollect = false;

    private Boolean      userLike    = false;

    private String       sliderImage;

    private List<String> sliderImageArr;

    public List<String> getSliderImageArr() {
        if (StrUtil.isNotEmpty(sliderImage)) {
            return Arrays.asList(sliderImage.split(","));
        }

        return new ArrayList<>();

    }

    private StoreProductAttrValue attrInfo;

    private String                productName;

    private String                storeInfo;

    private String                keyword;

    private String                barCode;

    private String                cateId;

    private BigDecimal            price;

    private BigDecimal            vipPrice;

    private BigDecimal            otPrice;

    private BigDecimal            postage;

    private String                unitName;

    private Integer               sort;

    private Integer               sales;

    private Integer               stock;

    private Integer               isShow;

    private Integer               isHot;

    private Integer               isBenefit;

    private Integer               isBest;

    private Integer               isNew;

    private String                description;

    private Integer               addTime;

    private Integer               isPostage;

    private Integer               isDel;

    private Integer               merUse;

    private BigDecimal            giveIntegral;

    private BigDecimal            cost;

    private Integer               isSeckill;

    private Integer               isBargain;

    private Integer               isGood;

    private Integer               ficti;

    private Integer               browse;

    private String                codePath;

    private String                soureLink;

    private Long                  storeId;
}
