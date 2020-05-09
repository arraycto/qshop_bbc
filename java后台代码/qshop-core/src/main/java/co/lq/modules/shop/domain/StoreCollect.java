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
* @date 2020-04-05
*/
@Entity
@Data
@Table(name="store_collect")
public class StoreCollect implements Serializable {

    /** 商品点赞ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 用户ID */
    @Column(name = "uid",nullable = false)
    @NotNull
    private Integer uid;

    /** 店铺ID */
    @Column(name = "store_id",nullable = false)
    @NotNull
    private Long storeId;

    /** 类型(收藏(collect）、点赞(like)) */
    @Column(name = "type",nullable = false)
    @NotBlank
    private String type;

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

    public void copy(StoreCollect source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}