package co.lq.modules.system.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author billy
 * @date 2019年9月7日 16:16:59
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "platform_user_avatar")
public class PlatformUserAvatar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;

    private String  realName;

    private String  path;

    private String  size;

    @Column(name = "add_time")
    private Date    addTime;

    @Column(name = "modify_time")
    private Date    modifyTime;

    @Column(name = "deleted")
    private Integer deleted;

    public PlatformUserAvatar(PlatformUserAvatar platformUserAvatar, String realName, String path, String size) {
        this.id = ObjectUtil.isNotEmpty(platformUserAvatar) ? platformUserAvatar.getId() : null;
        this.realName = realName;
        this.path = path;
        this.size = size;
    }
}
