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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author billy
 * @date 2018-11-22
 */
@Entity
@Getter
@Setter
@Table(name = "platform_user")
public class PlatformUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    private Long               id;

    @NotBlank
    @Column(unique = true)
    private String             username;

    /** 用户昵称 */
    @NotBlank
    private String             nickName;

    /** 性别 */
    private String             sex;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private PlatformUserAvatar platformUserAvatar;

    @NotBlank
    @Email
    private String             email;

    @NotBlank
    private String             phone;

    @NotNull
    private Boolean            enabled;

    private String             password;

    @Column(name = "last_password_reset_time")
    private Date               lastPasswordResetTime;

    @ManyToMany
    @JoinTable(name = "platform_users_roles", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id") })
    private Set<PlatformRole>  platformRoles;

    @OneToOne
    @JoinColumn(name = "job_id")
    private PlatformJob        platformJob;

    @OneToOne
    @JoinColumn(name = "dept_id")
    private PlatformDept       platformDept;

    @Column(name = "add_time")
    private Date               addTime;

    @Column(name = "modify_time")
    private Date               modifyTime;

    @Column(name = "deleted")
    private Integer            deleted;

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
        PlatformUser platformUser = (PlatformUser) o;
        return Objects.equals(id, platformUser.id) && Objects.equals(username, platformUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
