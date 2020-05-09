package co.lq.modules.shop.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Data
public class SmsContentDTO implements Serializable {

    /** 编号 */
    private Long id;

    /** 内容标题 */
    private String name;

    /** 消息类型 */
    private String type;

    /** 标签说明 */
    private String meno;

    /** 短信模版CODE */
    private String code;

    /** 内容模版 */
    private String remark;

    /** 所属店铺 */
    private Long storeId;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer deleted;
}