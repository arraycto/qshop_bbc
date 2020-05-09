package co.lq.modules.system.domain.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author billy
 * @date 2018-12-20
 */
@Data
@AllArgsConstructor
public class MenuMetaVo implements Serializable {

    private String  title;

    private String  icon;

    private Boolean noCache;
}
