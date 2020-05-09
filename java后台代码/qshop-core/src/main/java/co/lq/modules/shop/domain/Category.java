package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Getter;
import lombok.Setter;

/**
 * @author billy
 * @date 2019-10-03
 */
@Entity
@Getter
@Setter
@Table(name = "category")
public class Category implements Serializable {

    // 商品分类表ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    // 父id
    @Column(name = "pid", nullable = false)
    private Long       pid;

    // 分类名称
    @Column(name = "cate_name", nullable = false)
    @NotBlank(message = "分类名称不能为空")
    private String     cateName;

    // 排序
    @Column(name = "sort", nullable = false)
    private Integer    sort;

    // 图标
    @Column(name = "pic", nullable = false)
    //@NotBlank(message = "请上传分类图片")
    private String     pic;

    // 是否推荐
    @Column(name = "is_show", nullable = false)
    private Integer    isShow;

    // 添加时间
    @Column(name = "add_time", nullable = false)
    private Integer    addTime;

    // 修改时间
    @Column(name = "modify_time", nullable = false)
    private Integer    modifyTime;

    // 是否删除
    @Column(name = "deleted")
    private Integer    deleted;

    // 类目id
    @Column(name = "catalog_id", nullable = false)
    private Long       catalogId;

    @Column(name = "commission_rate", nullable = false)
    private BigDecimal commissionRate;

    public void copy(Category source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
