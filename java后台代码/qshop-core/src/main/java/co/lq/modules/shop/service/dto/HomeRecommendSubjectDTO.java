package co.lq.modules.shop.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Data
public class HomeRecommendSubjectDTO implements Serializable {

    private Long id;

    private Long subjectId;

    private String subjectName;

    private Integer recommendStatus;

    private Integer sort;

    /** 所属店铺 */
    private Long storeId;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer deleted;
}