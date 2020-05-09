package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-27
 */
@Data
public class StoreBrandDTO implements Serializable {

    private Long      id;

    /** 品牌名称 */
    private String    name;

    /** 首字母 */
    private String    firstLetter;

    /** 排序 */
    private Integer   sort;

    /** 品牌制造商 */
    private String    factory;

    /** 显示 */
    private Integer   showStatus;

    /** 产品数量 */
    private Integer   productCount;

    /** 品牌logo */
    private String    logo;

    /** 专区大图 */
    private String    bigPic;

    /** 品牌故事 */
    private String    brandStory;

    /** 所属店铺 */
    private String    shopName;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer   deleted;
}
