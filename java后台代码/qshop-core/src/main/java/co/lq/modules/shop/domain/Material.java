package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2020-01-09
 */
@Entity
@Data
@Table(name = "material")
public class Material implements Serializable {

    /** PK */
    @Id
    @Column(name = "id")
    private String    id;

    /** 逻辑删除标记（0：显示；1：隐藏） */
    @Column(name = "del_flag", nullable = false, insertable = false)
    private String    delFlag;

    /** 创建时间 */
    @Column(name = "create_time", nullable = false)
    @CreationTimestamp
    private Timestamp createTime;

    /** 创建者ID */
    @Column(name = "create_id")
    private String    createId;

    /** 类型1、图片；2、视频 */
    @Column(name = "type", nullable = false)
    private String    type;

    /** 分组ID */
    @Column(name = "group_id")
    private String    groupId;

    /** 素材名 */
    @Column(name = "name", nullable = false)
    @NotBlank
    private String    name;

    /** 素材链接 */
    @Column(name = "url")
    private String    url;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Long      storeId;

    public void copy(Material source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
