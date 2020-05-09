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
 * @date 2020-03-27
 */
@Entity
@Data
@Table(name = "cms_subject_category")
public class CmsSubjectCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long      id;

    /** 标题 */
    @Column(name = "name")
    private String    name;

    /** 分类图标 */
    @Column(name = "icon")
    private String    icon;

    /** 专题数量 */
    @Column(name = "subject_count")
    private Integer   subjectCount;

    /** 状态 */
    @Column(name = "show_status")
    private Integer   showStatus;

    /** 排序 */
    @Column(name = "sort")
    private Integer   sort;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Long      storeId;

    /** 添加时间 */
    @Column(name = "add_time")
    private Timestamp addTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Timestamp modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted")
    private Integer   deleted;

    public void copy(CmsSubjectCategory source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
