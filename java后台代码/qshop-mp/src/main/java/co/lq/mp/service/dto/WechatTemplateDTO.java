package co.lq.mp.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author billy
 * @date 2019-12-10
 */
@Data
public class WechatTemplateDTO implements Serializable {

    // 模板id
    private Integer id;

    // 模板编号
    private String  tempkey;

    // 模板名
    private String  name;

    // 回复内容
    private String  content;

    // 模板ID
    private String  tempid;

    // 添加时间
    private String  addTime;

    // 状态
    private Integer status;
}
