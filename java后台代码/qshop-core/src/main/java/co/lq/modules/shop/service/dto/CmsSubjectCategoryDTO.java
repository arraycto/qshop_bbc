package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-27
 */
@Data
public class CmsSubjectCategoryDTO implements Serializable {

    private Long      id;

    /** 标题 */
    private String    name;

    /** 分类图标 */
    private String    icon;

    /** 专题数量 */
    private Integer   subjectCount;

    /** 状态 */
    private Integer   showStatus;

    /** 排序 */
    private Integer   sort;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer   deleted;
}
