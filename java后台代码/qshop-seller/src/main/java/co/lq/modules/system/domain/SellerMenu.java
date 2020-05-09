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
 * @date 2018-12-17
 */
@Entity
@Getter
@Setter
@Table(name = "menu")
public class SellerMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = { Update.class })
    private Long            id;

    @NotBlank
    private String          name;

    @Column(unique = true)
    private Long            sort = 999L;

    @Column(name = "path")
    private String          path;

    private String          component;

    /** 类型，目录、菜单、按钮 */
    @Column(name = "type")
    private Integer         type;

    /** 权限 */
    @Column(name = "permission")
    private String          permission;

    @Column(unique = true, name = "component_name")
    private String          componentName;

    private String          icon;

    @Column(columnDefinition = "bit(1) default 0")
    private Boolean         cache;

    @Column(columnDefinition = "bit(1) default 0")
    private Boolean         hidden;

    /** 上级菜单ID */
    @Column(name = "pid", nullable = false)
    private Long            pid;

    /** 是否为外链 true/false */
    @Column(name = "i_frame")
    private Boolean         iFrame;

    @ManyToMany(mappedBy = "sellerMenus")
    @JsonIgnore
    private Set<SellerRole> sellerRoles;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp       createTime;

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
        SellerMenu sellerMenu = (SellerMenu) o;
        return Objects.equals(id, sellerMenu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
