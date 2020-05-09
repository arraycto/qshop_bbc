package co.lq.mp.domain;

import java.io.Serializable;

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
 * @author billy
 * @date 2019-10-10
 */
@Entity
@Data
@Table(name = "wechat_reply")
public class WechatReply implements Serializable {

    // 微信关键字回复id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // 关键字
    @Column(name = "`key`", unique = true, nullable = false)
    private String  key;

    // 回复类型
    @Column(name = "type", nullable = false)
    private String  type;

    // 回复数据
    @Column(name = "data", nullable = false)
    private String  data;

    // 0=不可用  1 =可用
    @Column(name = "status", nullable = false)
    private Integer status;

    // 是否隐藏
    @Column(name = "hide", nullable = false)
    private Integer hide;

    public void copy(WechatReply source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
