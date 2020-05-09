package co.lq.mp.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author billy
 * @date 2019-10-10
 */
@Data
public class WechatReplyDTO implements Serializable {

    // 微信关键字回复id
    private Integer id;

    // 关键字
    private String  key;

    // 回复类型
    private String  type;

    // 回复数据
    private String  data;

    // 0=不可用  1 =可用
    private Integer status;

    // 是否隐藏
    private Integer hide;
}
