package co.lq.modules.mnt.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Entity
@Data
@Table(name = "mnt_database")
public class Database implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id")
    private String    id;

    /**
     * 数据库名称
     */
    @Column(name = "name", nullable = false)
    private String    name;

    /**
     * 数据库连接地址
     */
    @Column(name = "jdbc_url", nullable = false)
    private String    jdbcUrl;

    /**
     * 数据库密码
     */
    @Column(name = "pwd", nullable = false)
    private String    pwd;

    /**
     * 用户名
     */
    @Column(name = "user_name", nullable = false)
    private String    userName;

    @CreationTimestamp
    private Timestamp createTime;

    public void copy(Database source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
