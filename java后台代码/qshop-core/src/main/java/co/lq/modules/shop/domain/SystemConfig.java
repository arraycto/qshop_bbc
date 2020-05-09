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
 * @date 2019-10-10
 */
@Entity
@Data
@Table(name = "system_config")
public class SystemConfig implements Serializable {

    // 配置id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // 字段名称
    @Column(name = "menu_name", nullable = false)
    private String  menuName;

    // 默认值
    @Column(name = "value")
    private String  value;

    // 排序
    @Column(name = "sort", nullable = false)
    private Integer sort;

    // 是否隐藏
    @Column(name = "status", nullable = false)
    private Integer status;

    public void copy(SystemConfig source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
