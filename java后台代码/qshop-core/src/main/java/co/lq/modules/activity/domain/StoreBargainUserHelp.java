package co.lq.modules.activity.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * 砍价帮助记录
 *
 * @author songbin
 * @since 2020年3月22日 下午4:36:02
 */
@Entity
@Data
@Table(name = "store_bargain_user_help")
public class StoreBargainUserHelp implements Serializable {
    // 砍价产品ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    // 参与砍价的用户ID
    @Column(name = "uid")
    private Long       uid;

    // 砍价产品id
    @Column(name = "bargain_id")
    private Long       bargainId;

    // 用户参与砍价表id
    @Column(name = "bargain_user_id")
    private Long       bargainUserId;

    // 帮助砍价多少金额
    @Column(name = "price")
    private BigDecimal price;

    // 砍价结束时间
    @Column(name = "add_time", nullable = false)
    private Integer    addTime;

    // 店铺id
    @Column(name = "store_id", nullable = false)
    private Long       storeId;

    public void copy(StoreBargainUserHelp source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
