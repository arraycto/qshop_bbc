package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-10
 */
@Entity
@Data
@Table(name = "catalog_attr")
public class CatalogAttr implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long    id;

    @Column(name = "attr_name")
    private String  attrName;

    /** 是否规格 */
    @Column(name = "is_spec")
    private Integer isSpec;

    @Column(name = "pic")
    private String  pic;

    /** 是否显示 */
    @Column(name = "is_show")
    private Integer isShow;

    /**
     * 属性选择类型
     */
    @Column(name = "select_type")
    private Integer selectType;

    /**
     * 属性录入方式
     */
    @Column(name = "input_type")
    private Integer inputType;

    /**
     * 可选值列表
     */
    @Column(name = "input_list")
    private String  inputList;

    /**
     * 排序字段
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 分类筛选样式
     */
    @Column(name = "filter_type")
    private Integer filterType;

    /**
     * 检索类型
     */
    @Column(name = "search_type")
    private Integer searchType;

    /**
     * 相同属性产品是否关联
     */
    @Column(name = "related_status")
    private Integer relatedStatus;

    /**
     * 是否支持手动新增
     */
    @Column(name = "hand_add_status")
    private Integer handAddStatus;

    /** 添加时间 */
    @Column(name = "add_time")
    private Date    addTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Date    modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted")
    private Integer deleted;

    // 类目id
    @Column(name = "catalog_id", nullable = false)
    private Long    catalogId;

    public void copy(CatalogAttr source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
