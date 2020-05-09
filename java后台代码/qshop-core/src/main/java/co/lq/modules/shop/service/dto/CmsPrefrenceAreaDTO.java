package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2020-04-11
 */
@Data
public class CmsPrefrenceAreaDTO implements Serializable {

    private Long      id;

    /** 标题 */
    private String    name;

    /** 子标题 */
    private String    subTitle;

    /** 展示图片 */
    private String    pic;

    /** 排序 */
    private Integer   sort;

    /** 状态 */
    private Integer   showStatus;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer   deleted;
}
