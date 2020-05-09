package co.lq.modules.shop.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author billy
 * @date 2019-12-04
 */
@Data
public class UserTaskDTO implements Serializable {

    private Long    id;

    // 任务名称
    private String  name;

    // 配置原名
    private String  realName;

    // 任务类型
    private String  taskType;

    // 限定数
    private Integer number;

    // 等级id
    private Long    levelId;

    private String  levalName;

    // 排序
    private Integer sort;

    // 是否显示
    private Integer isShow;

    // 是否务必达成任务,1务必达成,0=满足其一
    private Integer isMust;

    // 任务说明
    private String  illustrate;

    // 新增时间
    private Integer addTime;
}
