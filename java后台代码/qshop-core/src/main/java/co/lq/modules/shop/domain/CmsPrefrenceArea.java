package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.sql.Timestamp;

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
 * @date 2020-04-11
 */
@Entity
@Data
@Table(name = "cms_prefrence_area")
public class CmsPrefrenceArea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long      id;

    /** 标题 */
    @Column(name = "name")
    private String    name;

    /** 子标题 */
    @Column(name = "sub_title")
    private String    subTitle;

    /** 展示图片 */
    @Column(name = "pic")
    private String    pic;

    /** 排序 */
    @Column(name = "sort")
    private Integer   sort;

    /** 状态 */
    @Column(name = "show_status")
    private Integer   showStatus;

    /** 添加时间 */
    @Column(name = "add_time")
    private Timestamp addTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Timestamp modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted")
    private Integer   deleted;

    public void copy(CmsPrefrenceArea source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
