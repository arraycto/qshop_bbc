package co.lq.modules.system.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author billy
 * @date 2019-03-29
 */
@Getter
@Setter
@NoArgsConstructor
public class PlatformJobDTO implements Serializable {

    private Long            id;

    private Long            sort;

    private String          name;

    private Boolean         enabled;

    private PlatformDeptDTO platformDept;

    private String          deptSuperiorName;

    private Timestamp       createTime;

    public PlatformJobDTO(String name, Boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }
}
