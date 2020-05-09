package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-10
 */
@Entity
@Data
@Table(name = "store_category_attr")
public class StoreCategoryAttr implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long      id;

    /** 店铺id */
    @Column(name = "store_id", nullable = false)
    @NotNull
    private Long      storeId;

    /** 分类id */
    @Column(name = "category", nullable = false)
    @NotNull
    private Long      category;

    @Column(name = "attr_name")
    private String    attrName;

    /** 属性数量 */
    @Column(name = "attr_value")
    private String    attrValue;

    /** 是否显示 */
    @Column(name = "is_show")
    private Integer   isShow;

    /** 添加时间 */
    @Column(name = "add_time")
    private Timestamp addTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Timestamp modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted", nullable = false)
    @NotNull
    private Integer   deleted;

    public void copy(StoreCategoryAttr source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
