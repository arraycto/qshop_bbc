package co.lq.modules.shop.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Entity
@Data
@Table(name="sms_content")
public class SmsContent implements Serializable {

    /** 编号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 内容标题 */
    @Column(name = "name")
    private String name;

    /** 消息类型 */
    @Column(name = "type")
    private String type;

    /** 标签说明 */
    @Column(name = "meno")
    private String meno;

    /** 短信模版CODE */
    @Column(name = "code")
    private String code;

    /** 内容模版 */
    @Column(name = "remark")
    private String remark;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Long storeId;

    /** 添加时间 */
    @Column(name = "add_time",nullable = false)
    @NotNull
    private Timestamp addTime;

    /** 更新时间 */
    @Column(name = "modify_time",nullable = false)
    @NotNull
    private Timestamp modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted",nullable = false)
    @NotNull
    private Integer deleted;

    public void copy(SmsContent source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}