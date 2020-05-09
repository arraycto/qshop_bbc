package co.lq.modules.shop.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Entity
@Data
@Table(name="store_gifts")
public class StoreGifts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 类别 */
    @Column(name = "category_id")
    private Long categoryId;

    /** 图片 */
    @Column(name = "icon")
    private String icon;

    /** 标题 */
    @Column(name = "title")
    private String title;

    /** 状态 */
    @Column(name = "show_status")
    private Integer showStatus;

    /** 内容 */
    @Column(name = "content")
    private String content;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Integer storeId;

    /** 1 赠品 2 活动商品 */
    @Column(name = "type")
    private Integer type;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "stock")
    private Integer stock;

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

    public void copy(StoreGifts source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}