package co.lq.modules.shop.service;

import java.io.Serializable;
import java.util.List;

import co.lq.modules.shop.entity.SystemUser;
import co.lq.modules.shop.web.param.SellerUserQueryParam;
import co.lq.modules.shop.web.vo.SellerUserQueryVo;

/**
 * 卖家帐号服务
 *
 * @author songbin
 * @since 2020年3月31日 下午3:20:37
 */
public interface ApiSellerUserService {
    /**
     * 根据ID获取查询对象
     *
     * @param id 主键
     * @return StoreSettleQueryVo
     */
    SellerUserQueryVo getSellerUserById(Serializable id);

    /**
     * 获取店铺列表
     *
     * @return List<StoreSettleQueryVo>
     */
    List<SellerUserQueryVo> getList();

    /**
     * 根据ID获取查询对象
     *
     * @param sellerUserQueryParam 查询条件
     * @return StoreSettleQueryVo
     */
    SellerUserQueryVo getSellerUserByUsername(SellerUserQueryParam sellerUserQueryParam);

    /**
     * 保存店铺信息
     *
     * @param systemUser 店铺数据
     */
    Boolean saveSellerUser(SystemUser systemUser);
}
