package co.lq.modules.system.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * @author billy
 * @date 2019-04-10
 */
@Getter
@Setter
public class DictDetailDTO implements Serializable {

    private Long         id;

    private String       label;

    private String       value;

    private String       sort;

    private DictSmallDto dict;

    private Timestamp    createTime;
}
