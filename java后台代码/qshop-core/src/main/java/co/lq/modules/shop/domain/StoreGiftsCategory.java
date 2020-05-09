package co.lq.modules.shop.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Entity
@Data
@Table(name="store_gifts_category")
public class StoreGiftsCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 标题 */
    @Column(name = "name")
    private String name;

    /** 分类图标 */
    @Column(name = "icon")
    private String icon;

    /** 状态 */
    @Column(name = "show_status")
    private Integer showStatus;

    /** 排序 */
    @Column(name = "sort")
    private Integer sort;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Integer storeId;

    /** 添加时间 */
    @Column(name = "add_time",nullable = false)
    @NotNull
    private Timestamp addTime;

    /** 更新时间 */
    @Column(name = "modify_time",nullable = false)
    @NotNull
    private Timestamp modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted",nullable = false)
    @NotNull
    private Integer deleted;

    public void copy(StoreGiftsCategory source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}