package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2020-04-15
 */
@Entity
@Data
@Table(name = "store_complaint")
public class StoreComplaint implements Serializable {

    /** 分销商品id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long      id;

    /** 会员id */
    @Column(name = "uid")
    private Long      uid;

    /** 所属店铺 */
    @OneToOne
    @JoinColumn(name = "uid", insertable = false, updatable = false)
    private User      user;

    /** 投诉原因 */
    @Column(name = "reason")
    private String    reason;

    /** 投诉内容 */
    @Column(name = "desc")
    private String    desc;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Long      storeId;

    /** 所属店铺 */
    @OneToOne
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private Shop      shop;

    /** 添加时间 */
    @Column(name = "add_time")
    private Timestamp addTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Timestamp modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted")
    private Integer   deleted;

    public void copy(StoreComplaint source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
