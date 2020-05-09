package co.lq.modules.activity.web.dto;

import java.io.Serializable;

import co.lq.modules.activity.web.vo.StoreSeckillQueryVo;
import co.lq.modules.shop.web.vo.StoreProductReplyQueryVo;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 秒杀产品表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2019-12-17
 */
@Data
@Builder
public class StoreSeckillDTO implements Serializable {
    private static final long        serialVersionUID = 1L;

    private StoreProductReplyQueryVo reply;

    private Integer                  replyCount;

    private StoreSeckillQueryVo      storeInfo;

}
