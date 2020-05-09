package co.lq.modules.monitor.domain;

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
 * pv 与 ip 统计
 *
 * @author billy
 * @date 2018-12-13
 */
@Entity
@Data
@Table(name = "visits")
public class Visits implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long      id;

    @Column(unique = true)
    private String    date;

    @Column(name = "pv_counts")
    private Long      pvCounts;

    @Column(name = "ip_counts")
    private Long      ipCounts;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "week_day")
    private String    weekDay;
}
