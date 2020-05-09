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

import lombok.Getter;
import lombok.Setter;

/**
 * @author billy
 * @date 2019-03-29
 */
@Entity
@Getter
@Setter
@Table(name = "platform_job")
public class PlatformJob implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long         id;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String       name;

    @Column(unique = true)
    @NotNull
    private Long         sort;

    @Column(name = "enabled", nullable = false)
    @NotNull
    private Boolean      enabled;

    @OneToOne
    @JoinColumn(name = "dept_id")
    private PlatformDept platformDept;

    @Column(name = "add_time")
    private Timestamp    addTime;

    @Column(name = "modify_time")
    private Timestamp    modifyTime;

    @Column(name = "deleted")
    private Integer      deleted;

    public @interface Update {
    }
}
