package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-27
 */
@Entity
@Data
@Table(name = "store_brand")
public class StoreBrand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long      id;

    /** 品牌名称 */
    @Column(name = "name")
    private String    name;

    /** 首字母 */
    @Column(name = "first_letter")
    private String    firstLetter;

    /** 排序 */
    @Column(name = "sort")
    private Integer   sort;

    /** 品牌制造商 */
    @Column(name = "factory")
    private String    factory;

    /** 显示 */
    @Column(name = "show_status")
    private Integer   showStatus;

    /** 产品数量 */
    @Column(name = "product_count")
    private Integer   productCount;

    /** 品牌logo */
    @Column(name = "logo")
    private String    logo;

    /** 专区大图 */
    @Column(name = "big_pic")
    private String    bigPic;

    /** 品牌故事 */
    @Column(name = "brand_story")
    private String    brandStory;

    /** 所属店铺 */
    @OneToOne
    @JoinColumn(name = "store_id")
    private Shop      shop;

    /** 添加时间 */
    @Column(name = "add_time", nullable = false)
    private Timestamp addTime;

    /** 更新时间 */
    @Column(name = "modify_time", nullable = false)
    private Timestamp modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted", nullable = false)
    private Integer   deleted;

    public void copy(StoreBrand source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
