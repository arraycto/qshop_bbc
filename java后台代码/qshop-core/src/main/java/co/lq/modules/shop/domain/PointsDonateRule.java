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
@Table(name="points_donate_rule")
public class PointsDonateRule implements Serializable {

    /** 编号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 赠送类别 */
    @Column(name = "donate_type")
    private String donateType;

    /** 赠送条件 */
    @Column(name = "donate_condtion")
    private BigDecimal donateCondtion;

    /** 赠送积分 */
    @Column(name = "donate_integration")
    private Integer donateIntegration;

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

    public void copy(PointsDonateRule source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}