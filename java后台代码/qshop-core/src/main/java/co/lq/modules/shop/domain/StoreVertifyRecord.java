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
 * @date 2020-03-28
 */
@Entity
@Data
@Table(name = "store_vertify_record")
public class StoreVertifyRecord implements Serializable {

    /** 序号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long      id;

    /** 审核人 */
    @Column(name = "vertifier")
    private String    vertifier;

    /** 状态 */
    @Column(name = "status")
    private Integer   status;

    /** 反馈详情 */
    @Column(name = "detail")
    private String    detail;

    /** 店铺入驻信息id */
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

    public void copy(StoreVertifyRecord source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
