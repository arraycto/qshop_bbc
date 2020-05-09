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
@Table(name = "cms_subject")
public class CmsSubject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long      id;

    /** 分类 */
    @Column(name = "category_id")
    private Long      categoryId;

    /** 标题 */
    @Column(name = "title")
    private String    title;

    /** 专题主图 */
    @Column(name = "pic")
    private String    pic;

    /** 关联产品数量 */
    @Column(name = "product_count")
    private Integer   productCount;

    /** 推荐 */
    @Column(name = "recommend_status")
    private Integer   recommendStatus;

    /** 收藏量 */
    @Column(name = "collect_count")
    private Integer   collectCount;

    /** 点击量 */
    @Column(name = "read_count")
    private Integer   readCount;

    /** 评论量 */
    @Column(name = "comment_count")
    private Integer   commentCount;

    /** 画册图片用逗号分割 */
    @Column(name = "album_pics")
    private String    albumPics;

    /** 描述 */
    @Column(name = "description")
    private String    description;

    /** 显示状态：0->不显示；1->显示 */
    @Column(name = "show_status")
    private Integer   showStatus;

    /** 内容 */
    @Column(name = "content")
    private String    content;

    /** 转发数 */
    @Column(name = "forward_count")
    private Integer   forwardCount;

    /** 专题分类名称 */
    @Column(name = "category_name")
    private String    categoryName;

    @Column(name = "member_id")
    private Long      memberId;

    /** 打赏 */
    @Column(name = "reward")
    private Integer   reward;

    @Column(name = "area_name")
    private String    areaName;

    @Column(name = "school_name")
    private String    schoolName;

    @Column(name = "member_name")
    private String    memberName;

    @Column(name = "video_src")
    private String    videoSrc;

    @Column(name = "type")
    private Integer   type;

    /** 添加时间 */
    @Column(name = "add_time")
    private Timestamp addTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Timestamp modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted")
    private Integer   deleted;

    public void copy(CmsSubject source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
