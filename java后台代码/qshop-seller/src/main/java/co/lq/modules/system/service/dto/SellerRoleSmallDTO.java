package co.lq.modules.system.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author billy
 * @date 2018-11-23
 */
@Data
public class SellerRoleSmallDTO implements Serializable {

    private Long    id;

    private String  name;

    private Integer level;

    private String  dataScope;

    private String  permission;
}
