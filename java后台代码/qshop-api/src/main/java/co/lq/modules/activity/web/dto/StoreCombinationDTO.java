package co.lq.modules.activity.web.dto;

import java.io.Serializable;
import java.util.List;

import co.lq.modules.activity.web.vo.StoreCombinationQueryVo;
import co.lq.modules.shop.web.vo.StoreProductReplyQueryVo;
import lombok.Data;

/**
 * <p>
 * 拼团产品表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2019-11-19
 */
@Data
public class StoreCombinationDTO implements Serializable {
    private static final long        serialVersionUID = 1L;

    private List<PinkDTO>            pink;

    private List<Long>               pindAll;

    private List<String>             pinkOkList;

    private Integer                  pinkOkSum;

    private StoreProductReplyQueryVo reply;

    private Integer                  replyCount       = 0;

    private String                   replyChance;
    private StoreCombinationQueryVo  storeInfo;

}
