package co.lq.modules.mnt.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "mnt_server")
public class ServerDeploy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long      id;

    private String    name;

    private String    ip;

    private Integer   port;

    private String    account;

    private String    password;

    @CreationTimestamp
    private Timestamp createTime;

    public void copy(ServerDeploy source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
