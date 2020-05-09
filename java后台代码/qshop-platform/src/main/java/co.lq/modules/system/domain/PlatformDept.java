package co.lq.modules.system.domain;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "platform_dept")
public class PlatformDept implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long              id;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String            name;

    @NotNull
    private Boolean           enabled;

    @Column(name = "pid", nullable = false)
    @NotNull
    private Long              pid;

    @JsonIgnore
    @ManyToMany(mappedBy = "platformDepts")
    private Set<PlatformRole> platformRoles;

    @Column(name = "add_time")
    private Date              addTime;

    @Column(name = "modify_time")
    private Date              modifyTime;

    @Column(name = "deleted")
    private Integer           deleted;

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
        PlatformDept platformDept = (PlatformDept) o;
        return Objects.equals(id, platformDept.id) && Objects.equals(name, platformDept.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
