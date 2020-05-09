package co.lq.modules.system.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
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
@Table(name = "dict")
public class Dict implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long             id;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    private String           name;

    @Column(name = "remark")
    private String           remark;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp        createTime;

    @OneToMany(mappedBy = "dict", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<DictDetail> dictDetails;

    public @interface Update {
    }
}
