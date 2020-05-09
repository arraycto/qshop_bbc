package co.lq.modules.system.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * @author billy
 * @date 2019-04-10
 */
@Entity
@Getter
@Setter
@Table(name = "dict_detail")
public class DictDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long      id;

    /** 字典标签 */
    @Column(name = "label", nullable = false)
    private String    label;

    /** 字典值 */
    @Column(name = "value", nullable = false)
    private String    value;

    /** 排序 */
    @Column(name = "sort")
    private String    sort = "999";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dict_id")
    private Dict      dict;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    public @interface Update {
    }
}
