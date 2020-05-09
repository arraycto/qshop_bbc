package co.lq.modules.system.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import lombok.Data;

/**
 * @author billy
 * @date 2018-11-23
 */
@Data
public class SellerRoleDTO implements Serializable {

    private Long               id;

    private String             name;

    private String             dataScope;

    private Integer            level;

    private String             remark;

    private String             permission;

    private Set<SellerMenuDTO> menus;

    private Set<SellerDeptDTO> depts;

    private Timestamp          createTime;

    private Long               storeId;
}
