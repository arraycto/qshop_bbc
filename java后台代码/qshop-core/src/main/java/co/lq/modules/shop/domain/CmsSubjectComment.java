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
@Table(name = "cms_subject_comment")
public class CmsSubjectComment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long      id;

    /** 所属专题 */
    @Column(name = "subject_id")
    private Long      subjectId;

    /** 用户名 */
    @Column(name = "member_nick_name")
    private String    memberNickName;

    /** 用户图标 */
    @Column(name = "member_icon")
    private String    memberIcon;

    /** 内容 */
    @Column(name = "content")
    private String    content;

    /** 创建时间 */
    @Column(name = "create_time")
    private Timestamp createTime;

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

    public void copy(CmsSubjectComment source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
