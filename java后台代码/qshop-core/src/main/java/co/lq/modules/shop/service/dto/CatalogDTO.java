package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-10
 */
@Data
public class CatalogDTO implements Serializable {

    private Long             id;

    /** 类目名称 */
    private String           name;

    /** 类目关键字，以JSON数组格式 */
    private String           keywords;

    /** 类目广告语介绍 */
    private String           descs;

    /** 父类目ID */
    private Long             pid;

    /** 类目图标 */
    private String           iconUrl;

    /** 类目图片 */
    private String           picUrl;

    private Integer          level;

    /** 排序 */
    private Integer          sortOrder;

    /** 创建时间 */
    private Date             createTime;

    /** 更新时间 */
    private Date             modifyTime;

    /** 逻辑删除 */
    private Integer          deleted;

    //佣金率
    private BigDecimal       commissionRate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CatalogDTO> children;

    private List<Long>       catalogIdList;

    public String getLabel() {
        return name;
    }
}
