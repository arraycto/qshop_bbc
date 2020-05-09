package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-10
 */
@Data
public class CategoryAttrDTO implements Serializable {

    private Long    id;

    /** 分类id */
    private Long    category;

    private String  attrName;

    /** 属性数量 */
    private String  attrValue;

    /** 是否显示 */
    private Integer isShow;

    /** 添加时间 */
    private Date    addTime;

    /** 更新时间 */
    private Date    modifyTime;

    /** 逻辑删除 */
    private Integer deleted;
}
