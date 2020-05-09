package co.lq.modules.shop.service;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;

import co.lq.modules.shop.entity.Store;
import co.lq.modules.shop.entity.StoreCatalogRelation;
import co.lq.modules.shop.entity.SystemUser;
import co.lq.modules.shop.web.param.StoreQueryParam;
import co.lq.modules.shop.web.vo.StoreQueryVo;
import co.lq.modules.shop.web.vo.StoreVo;

/**
 * 店铺
 *
 * @author songbin
 * @since 2020年3月12日 下午9:13:46
 */
public interface ApiStoreServie {
    /**
     * 根据ID获取查询对象
     *
     * @param id 主键
     * @return StoreQueryVo
     */
    StoreQueryVo getStoreById(Serializable id);

    /**
     * 根据ID获取查询对象
     *
     * @param id 主键
     * @param uid 用户id
     * @return StoreQueryVo
     */
    StoreQueryVo getStoreByUidAndId(Serializable id, Long uid);

    /**
     * 获取店铺列表
     *
     * @param storeQueryParam
     * @return List<StoreDTO>
     */
    IPage<StoreQueryVo> getStorePageList(StoreQueryParam storeQueryParam);

    /**
     * 根据ID获取查询对象
     *
     * @param storeQueryParam 查询条件
     * @return StoreQueryVo
     */
    StoreQueryVo getShopInfoByUid(StoreQueryParam storeQueryParam);

    /**
     * 保存店铺信息
     *
     * @param storeVo 店铺数据
     */
    Store saveShop(StoreVo storeVo);

    /**
     * 保存店铺的入驻类目
     *
     * @param storeId
     * @param catalogId
     * @return
     */
    StoreCatalogRelation saveCatalogRelation(Long storeId, Long catalogId);

    /**
     * 创建管理员帐号和角色
     *
     * @param storeVo
     * @return
     */
    Boolean createAdminRole(StoreVo storeVo);

    /**
     * 店铺收藏和取消收藏
     *
     * @param id
     * @param uid
     * @param isChecked
     * @return
     */
    int collectStore(Long id, Long uid, Integer isChecked);

    /**
     * 关联角色和帐号
     *
     * @param storeVo
     * @return
     */
    Boolean relationAdminRole(StoreVo storeVo);

    /**
     * 获取特定数量的店铺
     *
     * @param storeQueryParam
     * @param start
     * @param limit
     * @return
     */
    List<StoreQueryVo> getShopList(StoreQueryParam storeQueryParam, int start, int limit);

    /**
     * 通过店铺id获取店铺首页
     *
     * @param storeId
     * @param uid
     * @return
     */
    StoreQueryVo getStoreHome(Long storeId, Long uid);

    /**
     * 通过username查询商家用户
     *
     * @param username
     * @return
     */
    SystemUser getSystemByUsername(String username);

    /**
     * 保存商户帐号
     *
     * @param systemUser
     */
    SystemUser saveSystemUser(SystemUser systemUser);
}
