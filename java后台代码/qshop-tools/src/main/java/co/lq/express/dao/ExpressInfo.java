/**
 * Copyright 2018 bejson.com
 */
package co.lq.express.dao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Auto-generated: 2018-07-19 22:27:22
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class ExpressInfo {

    @JsonProperty("LogisticCode")
    private String       LogisticCode;
    @JsonProperty("ShipperCode")
    private String       ShipperCode;
    @JsonProperty("Traces")
    private List<Traces> Traces;
    @JsonProperty("State")
    private String       State;
    @JsonProperty("EBusinessID")
    private String       EBusinessID;
    @JsonProperty("Success")
    private boolean      Success;
    @JsonProperty("Reason")
    private String       Reason;

    private String       ShipperName;

    @JsonProperty("OrderCode")
    private String       OrderCode;

}
