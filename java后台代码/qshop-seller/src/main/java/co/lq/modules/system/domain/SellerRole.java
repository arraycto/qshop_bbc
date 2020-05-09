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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * 角色
 *
 * @author billy
 * @date 2018-11-22
 */
@Entity
@Table(name = "role")
@Getter
@Setter
public class SellerRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = { Update.class })
    private Long            id;

    @Column(nullable = false)
    @NotBlank
    private String          name;

    /** 数据权限类型 全部 、 本级 、 自定义 */
    @Column(name = "data_scope")
    private String          dataScope = "本级";

    /** 数值越小，级别越大 */
    @Column(name = "level")
    private Integer         level     = 3;

    @Column
    private String          remark;

    /** 权限 */
    @Column(name = "permission")
    private String          permission;

    @JsonIgnore
    @ManyToMany(mappedBy = "sellerRoles")
    private Set<SellerUser> users;

    @ManyToMany
    @JoinTable(name = "roles_menus", joinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "menu_id", referencedColumnName = "id") })
    private Set<SellerMenu> sellerMenus;

    @ManyToMany
    @JoinTable(name = "roles_depts", joinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "dept_id", referencedColumnName = "id") })
    private Set<SellerDept> sellerDepts;

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
        SellerRole sellerRole = (SellerRole) o;
        return Objects.equals(id, sellerRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
