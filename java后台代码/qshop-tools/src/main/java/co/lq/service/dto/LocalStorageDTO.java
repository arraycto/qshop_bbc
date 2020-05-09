package co.lq.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2019-09-05
 */
@Data
public class LocalStorageDTO implements Serializable {

    private Long      id;

    private String    realName;

    private String    name;

    private String    suffix;

    private String    type;

    private String    size;

    private String    operate;

    private Timestamp createTime;
}
