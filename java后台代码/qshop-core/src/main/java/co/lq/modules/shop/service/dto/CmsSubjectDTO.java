package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-27
 */
@Data
public class CmsSubjectDTO implements Serializable {

    private Long      id;

    /** 分类 */
    private Long      categoryId;

    /** 标题 */
    private String    title;

    /** 专题主图 */
    private String    pic;

    /** 关联产品数量 */
    private Integer   productCount;

    /** 推荐 */
    private Integer   recommendStatus;

    /** 收藏量 */
    private Integer   collectCount;

    /** 点击量 */
    private Integer   readCount;

    /** 评论量 */
    private Integer   commentCount;

    /** 画册图片用逗号分割 */
    private String    albumPics;

    /** 描述 */
    private String    description;

    /** 显示状态：0->不显示；1->显示 */
    private Integer   showStatus;

    /** 内容 */
    private String    content;

    /** 转发数 */
    private Integer   forwardCount;

    /** 专题分类名称 */
    private String    categoryName;

    private Long      memberId;

    /** 打赏 */
    private Integer   reward;

    private String    areaName;

    private String    schoolName;

    private String    memberName;

    private String    videoSrc;

    private Integer   type;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer   deleted;
}
