package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2020-04-13
 */
@Entity
@Data
@Table(name = "store_withdraw_record")
public class StoreWithdrawRecord implements Serializable {

    /** 序号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long      id;

    /** 提现申请ID */
    @Column(name = "store_withdraw_id")
    private Long      storeWithdrawId;

    /** 审核人 */
    @Column(name = "vertifier")
    private String    vertifier;

    @Column(name = "status")
    private Integer   status;

    /** 反馈详情 */
    @Column(name = "detail")
    private String    detail;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Long      storeId;

    /** 添加时间 */
    @Column(name = "add_time")
    private Timestamp addTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Timestamp modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted")
    private Integer   deleted;

    public void copy(StoreWithdrawRecord source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
