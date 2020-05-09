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
 * @date 2020-03-11
 */
@Entity
@Data
@Table(name = "store_system_config")
public class StoreSystemConfig implements Serializable {

    /** 配置id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long      id;

    /** 字段名称 */
    @Column(name = "menu_name")
    private String    menuName;

    /** 默认值 */
    @Column(name = "value")
    private String    value;

    /** 排序 */
    @Column(name = "sort")
    private Integer   sort;

    /** 是否隐藏 */
    @Column(name = "status")
    private Integer   status;

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

    public void copy(StoreSystemConfig source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
