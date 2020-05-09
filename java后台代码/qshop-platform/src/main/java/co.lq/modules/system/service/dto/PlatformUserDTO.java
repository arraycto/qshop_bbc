package co.lq.modules.system.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author billy
 * @date 2018-11-23
 */
@Data
public class PlatformUserDTO implements Serializable {

    @ApiModelProperty(hidden = true)
    private Long                 id;

    private String               username;

    private String               nickName;

    private String               sex;

    private String               avatar;

    private String               email;

    private String               phone;

    private Boolean              enabled;

    @JsonIgnore
    private String               password;

    private Date                 lastPasswordResetTime;

    @ApiModelProperty(hidden = true)
    private Set<PlatformRoleDTO> platformRoles;

    @ApiModelProperty(hidden = true)
    private PlatformJobDTO       platformJob;

    private PlatformDeptDTO      platformDept;

    private Long                 deptId;

    private Timestamp            createTime;

    private Long                 storeId;
}
