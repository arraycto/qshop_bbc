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
@Table(name="points_coupon")
public class PointsCoupon implements Serializable {

    /** 编号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 有效期(天) */
    @Column(name = "valid_day")
    private Integer validDay;

    /** 最小积分 */
    @Column(name = "min_integration")
    private Integer minIntegration;

    /** 最大积分 */
    @Column(name = "max_integration")
    private Integer maxIntegration;

    /** 备注 */
    @Column(name = "remark")
    private String remark;

    /** 创建时间 */
    @Column(name = "create_time")
    private Timestamp createTime;

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

    @Column(name = "count")
    private Integer count;

    public void copy(PointsCoupon source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}