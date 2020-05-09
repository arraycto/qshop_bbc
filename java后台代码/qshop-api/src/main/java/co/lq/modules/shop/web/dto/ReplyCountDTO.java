package co.lq.modules.shop.web.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @ClassName ReplyCount
 * @Author billy
 * @Date 2019/11/4
 **/
@Data
public class ReplyCountDTO implements Serializable {
    private Integer sumCount;
    private Integer goodCount;
    private Integer inCount;
    private Integer poorCount;
    private String  replyChance;
    private String  replySstar;

}
