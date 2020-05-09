package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-10
 */
@Entity
@Data
@Table(name = "store_category")
public class StoreCategory implements Serializable {

    /** 商品分类表ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long      id;

    // 父id
    @Column(name = "pid")
    private Long      pid;

    /** 店铺id */
    @Column(name = "store_id")
    private Long      storeId;

    /** 分类名称 */
    @Column(name = "cate_name", nullable = false)
    @NotBlank
    private String    cateName;

    /** 排序 */
    @Column(name = "sort")
    private Integer   sort;

    /** 图标 */
    @Column(name = "pic")
    private String    pic;

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
    @Column(name = "deleted")
    private Integer   deleted;

    public void copy(StoreCategory source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
