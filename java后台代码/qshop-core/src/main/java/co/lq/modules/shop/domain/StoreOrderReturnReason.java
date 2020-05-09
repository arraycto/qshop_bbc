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
* @date 2020-03-29
*/
@Entity
@Data
@Table(name="store_order_return_reason")
public class StoreOrderReturnReason implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 退货类型 */
    @Column(name = "name")
    private String name;

    @Column(name = "sort")
    private Integer sort;

    /** 状态：0->不启用；1->启用 */
    @Column(name = "status")
    private Integer status;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Long storeId;

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

    public void copy(StoreOrderReturnReason source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}