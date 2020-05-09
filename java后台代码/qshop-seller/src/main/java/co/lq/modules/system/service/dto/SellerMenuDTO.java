package co.lq.modules.system.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

/**
 * @author billy
 * @date 2018-12-17
 */
@Data
public class SellerMenuDTO implements Serializable {

    private Long                id;

    private Integer             type;

    private String              permission;

    private String              name;

    private Long                sort;

    private String              path;

    private String              component;

    private Long                pid;

    private Boolean             iFrame;

    private Boolean             cache;

    private Boolean             hidden;

    private String              componentName;

    private String              icon;

    private List<SellerMenuDTO> children;

    private Timestamp           createTime;
}
