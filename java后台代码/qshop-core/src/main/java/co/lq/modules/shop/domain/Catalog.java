package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
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
@Table(name = "catalog")
public class Catalog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    /** 类目名称 */
    @Column(name = "name", nullable = false)
    @NotBlank
    private String     name;

    /** 类目关键字，以JSON数组格式 */
    @Column(name = "keywords", nullable = false)
    @NotBlank
    private String     keywords;

    /** 类目广告语介绍 */
    @Column(name = "descs")
    private String     descs;

    /** 父类目ID */
    @Column(name = "pid")
    private Long       pid;

    /** 类目图标 */
    @Column(name = "icon_url")
    @NotNull
    private String     iconUrl;

    /** 类目图片 */
    @Column(name = "pic_url")
    @NotNull
    private String     picUrl;

    @Column(name = "level")
    private Integer    level;

    /** 排序 */
    @Column(name = "sort_order")
    private Integer    sortOrder;

    /** 创建时间 */
    @Column(name = "create_time")
    private Date       createTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Date       modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted")
    private Integer    deleted;

    @Column(name = "charge_rate", nullable = false)
    private BigDecimal chargeRate;

    @Transient
    private List<Long> catalogIdList;

    public void copy(Catalog source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
