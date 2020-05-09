package co.lq.modules.system.service.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author billy
 * @date 2019-6-10 16:32:18
 */
@Data
@NoArgsConstructor
public class JobSmallDTO implements Serializable {

    private Long   id;

    private String name;
}
