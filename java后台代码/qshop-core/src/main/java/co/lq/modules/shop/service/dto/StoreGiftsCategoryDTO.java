package co.lq.modules.shop.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Data
public class StoreGiftsCategoryDTO implements Serializable {

    private Long id;

    /** 标题 */
    private String name;

    /** 分类图标 */
    private String icon;

    /** 状态 */
    private Integer showStatus;

    /** 排序 */
    private Integer sort;

    /** 所属店铺 */
    private Integer storeId;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer deleted;
}