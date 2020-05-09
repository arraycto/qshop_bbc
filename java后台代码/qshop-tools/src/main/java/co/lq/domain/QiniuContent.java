package co.lq.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

/**
 * 上传成功后，存储结果
 *
 * @author billy
 * @date 2018-12-31
 */
@Data
@Entity
@Table(name = "qiniu_content")
public class QiniuContent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long      id;

    /** 文件名 */
    @Column(name = "name")
    private String    key;

    /** 空间名 */
    private String    bucket;

    /** 大小 */
    private String    size;

    /** 文件地址 */
    private String    url;

    private String    suffix;

    /** 空间类型：公开/私有 */
    private String    type = "公开";

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;
}
