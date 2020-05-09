package co.lq.modules.user.web.param;

import java.io.Serializable;

import lombok.Data;

/**
 * @ClassName PromParam
 * @Author billy
 * @Date 2019/11/12
 **/
@Data
public class PromParam implements Serializable {
    private Integer grade;
    private String  keyword;
    private Integer limit;
    private Integer page;
    private String  sort;
}
