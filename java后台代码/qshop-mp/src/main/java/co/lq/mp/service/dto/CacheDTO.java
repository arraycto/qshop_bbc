package co.lq.mp.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author billy
 * @date 2019-10-06
 */
@Data
public class CacheDTO implements Serializable {

    private String  key;

    // 缓存数据
    private String  result;

    // 缓存时间
    private Integer addTime;
}
