package co.lq.modules.system.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * @author billy
 * @date 2019-03-29
 */
@Entity
@Getter
@Setter
@Table(name = "job")
public class SellerJob implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long       id;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String     name;

    @Column(unique = true)
    @NotNull
    private Long       sort;

    @Column(name = "enabled", nullable = false)
    @NotNull
    private Boolean    enabled;

    @OneToOne
    @JoinColumn(name = "dept_id")
    private SellerDept sellerDept;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp  createTime;

    // 所属门店
    @Column(name = "store_id", nullable = false)
    private Long       storeId;

    public @interface Update {
    }
}
