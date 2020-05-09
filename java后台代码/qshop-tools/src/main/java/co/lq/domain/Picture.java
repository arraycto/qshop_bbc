package co.lq.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

/**
 * sm.ms图床
 *
 * @author billy
 * @date 2018-12-27
 */
@Data
@Entity
@Table(name = "picture")
public class Picture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long      id;

    private String    filename;

    private String    url;

    private String    size;

    private String    height;

    private String    width;

    @Column(name = "delete_url")
    private String    delete;

    private String    username;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    /** 用于检测文件是否重复 */
    private String    md5Code;

    @Override
    public String toString() {
        return "Picture{" + "filename='" + filename + '\'' + '}';
    }
}
