package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.sql.Timestamp;

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
 * @date 2020-03-13
 */
@Entity
@Data
@Table(name = "home_advertise")
public class HomeAdvertise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long      id;

    @Column(name = "name")
    private String    name;

    /** 轮播位置：0->PC首页轮播；1->app首页轮播, 2-h5、小程序 */
    @Column(name = "type")
    private Integer   type;

    @Column(name = "pic")
    private String    pic;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    /** 上下线状态：0->下线；1->上线 */
    @Column(name = "status")
    private Integer   status;

    /** 点击数 */
    @Column(name = "click_count")
    private Integer   clickCount;

    /** 下单数 */
    @Column(name = "order_count")
    private Integer   orderCount;

    /** 链接地址 */
    @Column(name = "url")
    private String    url;

    /** 备注 */
    @Column(name = "note")
    private String    note;

    /** 排序 */
    @Column(name = "sort")
    private Integer   sort;

    /** 广告所属店铺 */
    @Column(name = "store_id")
    private Long      storeId;

    /** 创建时间 */
    @Column(name = "add_time")
    private Timestamp addTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Timestamp modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted")
    private Integer   deleted;

    public void copy(HomeAdvertise source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
