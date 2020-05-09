package co.lq.modules.system.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * @author billy
 * @date 2018-11-22
 */
@Entity
@Getter
@Setter
@Table(name = "system_user")
public class SellerUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    private Long             id;

    @NotBlank
    @Column(unique = true)
    private String           username;

    /** 用户昵称 */
    @NotBlank
    private String           nickName;

    /** 性别 */
    private String           sex;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private SellerUserAvatar sellerUserAvatar;

    @NotBlank
    @Email
    private String           email;

    @NotBlank
    private String           phone;

    @NotNull
    private Boolean          enabled;

    private String           password;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp        createTime;

    @Column(name = "last_password_reset_time")
    private Date             lastPasswordResetTime;

    @ManyToMany
    @JoinTable(name = "system_users_roles", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id") })
    private Set<SellerRole>  sellerRoles;

    @OneToOne
    @JoinColumn(name = "job_id")
    private SellerJob        sellerJob;

    @OneToOne
    @JoinColumn(name = "dept_id")
    private SellerDept       sellerDept;

    // 所属门店
    @Column(name = "store_id", nullable = false)
    private Long             storeId;

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
        SellerUser sellerUser = (SellerUser) o;
        return Objects.equals(id, sellerUser.id) && Objects.equals(username, sellerUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
