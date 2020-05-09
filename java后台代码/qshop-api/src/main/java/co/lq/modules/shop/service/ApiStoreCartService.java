package co.lq.modules.shop.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import co.lq.common.service.BaseService;
import co.lq.modules.shop.entity.StoreCart;
import co.lq.modules.shop.web.vo.StoreCartQueryVo;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-25
 */
public interface ApiStoreCartService extends BaseService<StoreCart> {

    void removeUserCart(long uid, List<String> ids);

    void changeUserCartNum(long cartId, int cartNum, long uid);

    Map<String, Object> getUserProductCartList(long uid, String cartIds, int status);

    int getUserCartNum(long uid, String type, int numType);

    int addCart(long uid, long productId, int cartNum, String productAttrUnique, String type, int isNew,
                long combinationId, long seckillId, long bargainId, long integralId);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreCartQueryVo getStoreCartById(Serializable id);

}
