package co.lq.modules.system.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * @author billy
 * @date 2019-03-25
 */
@Entity
@Getter
@Setter
@Table(name = "dept")
public class SellerDept implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long            id;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String          name;

    @NotNull
    private Boolean         enabled;

    @Column(name = "pid", nullable = false)
    @NotNull
    private Long            pid;

    @JsonIgnore
    @ManyToMany(mappedBy = "sellerDepts")
    private Set<SellerRole> sellerRoles;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp       createTime;

    // 所属门店
    @Column(name = "store_id", nullable = false)
    private Long            storeId;

    public @interface Update {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SellerDept sellerDept = (SellerDept) o;
        return Objects.equals(id, sellerDept.id) && Objects.equals(name, sellerDept.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
