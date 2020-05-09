package co.lq.modules.system.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "system_user_avatar")
public class SellerUserAvatar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long      id;

    private String    realName;

    private String    path;

    private String    size;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    // 所属门店
    @Column(name = "store_id", nullable = false)
    private Integer   storeId;

    public SellerUserAvatar(SellerUserAvatar sellerUserAvatar, String realName, String path, String size) {
        this.id = ObjectUtil.isNotEmpty(sellerUserAvatar) ? sellerUserAvatar.getId() : null;
        this.realName = realName;
        this.path = path;
        this.size = size;
    }
}
