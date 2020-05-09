package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-10
 */
@Data
public class CatalogAttrDTO implements Serializable {

    private Long       id;

    private String     attrName;

    /**
     * 属性选择类型
     */
    private Integer    selectType;

    /**
     * 属性录入方式
     */
    private Integer    inputType;

    /**
     * 可选值列表
     */
    private String     inputList;

    /**
     * 排序字段
     */
    private Integer    sort;

    /**
     * 分类筛选样式
     */
    private Integer    filterType;

    /**
     * 检索类型
     */
    private Integer    searchType;

    /**
     * 相同属性产品是否关联
     */
    private Integer    relatedStatus;

    /**
     * 是否支持手动新增
     */
    private Integer    handAddStatus;

    /** 是否规格 */
    private Integer    isSpec;

    private String     pic;

    /** 是否显示 */
    private Integer    isShow;

    /** 添加时间 */
    private Date       addTime;

    /** 更新时间 */
    private Date       modifyTime;

    /** 逻辑删除 */
    private Integer    deleted;

    // 类目id
    private Long       catalogId;

    private List<Long> catalogList;
}
