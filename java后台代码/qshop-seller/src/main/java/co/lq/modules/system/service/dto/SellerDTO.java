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
public class SellerDTO implements Serializable {

    @ApiModelProperty(hidden = true)
    private Long                    id;

    private String                  username;

    private String                  nickName;

    private String                  sex;

    private String                  avatar;

    private String                  email;

    private String                  phone;

    private Boolean                 enabled;

    @JsonIgnore
    private String                  password;

    private Date                    lastPasswordResetTime;

    @ApiModelProperty(hidden = true)
    private Set<SellerRoleSmallDTO> sellerRoles;

    @ApiModelProperty(hidden = true)
    private SellerJobDTO            sellerJob;

    private SellerDeptDTO           sellerDept;

    private Long                    deptId;

    private Timestamp               createTime;

    private Long                    storeId;
}
