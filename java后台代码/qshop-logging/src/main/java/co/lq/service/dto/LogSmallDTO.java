package co.lq.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2019-5-22
 */
@Data
public class LogSmallDTO implements Serializable {

    private String    description;

    private String    requestIp;

    private Long      time;

    private String    address;

    private String    browser;

    private Timestamp createTime;
}
