package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * @author billy
 * @date 2019-10-18
 */
@Data
public class SystemGroupDataDTO implements Serializable {

    // 组合数据详情ID
    private Long                id;

    // 对应的数据名称
    private String              groupName;

    // 数据组对应的数据值（json数据）
    private String              value;

    private Map<String, Object> map;

    // 添加数据时间
    private Integer             addTime;

    // 数据排序
    private Integer             sort;

    // 状态（1：开启；2：关闭；）
    private Integer             status;
}
