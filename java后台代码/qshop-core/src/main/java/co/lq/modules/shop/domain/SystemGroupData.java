package co.lq.modules.shop.domain;

import java.io.Serializable;

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
 * @date 2019-10-18
 */
@Entity
@Data
@Table(name = "system_group_data")
public class SystemGroupData implements Serializable {

    // 组合数据详情ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long    id;

    // 对应的数据名称
    @Column(name = "group_name", nullable = false)
    private String  groupName;

    // 数据组对应的数据值（json数据）
    @Column(name = "value", nullable = false)
    private String  value;

    // 添加数据时间
    @Column(name = "add_time", nullable = false)
    private Integer addTime;

    // 数据排序
    @Column(name = "sort", nullable = false)
    private Integer sort;

    // 状态（1：开启；2：关闭；）
    @Column(name = "status", nullable = false)
    private Integer status;

    public void copy(SystemGroupData source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
