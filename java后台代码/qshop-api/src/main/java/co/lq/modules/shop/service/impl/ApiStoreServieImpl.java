package co.lq.modules.shop.service.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.modules.shop.entity.Role;
import co.lq.modules.shop.entity.Store;
import co.lq.modules.shop.entity.StoreCatalogRelation;
import co.lq.modules.shop.entity.StoreCollect;
import co.lq.modules.shop.entity.SystemUser;
import co.lq.modules.shop.entity.SystemUsersRoles;
import co.lq.modules.shop.mapper.HomeAdvertiseMapper;
import co.lq.modules.shop.mapper.RoleMapper;
import co.lq.modules.shop.mapper.StoreCatalogRelationMapper;
import co.lq.modules.shop.mapper.StoreCollectMapper;
import co.lq.modules.shop.mapper.StoreMapper;
import co.lq.modules.shop.mapper.SystemUserMapper;
import co.lq.modules.shop.mapper.SystemUsersRolesMapper;
import co.lq.modules.shop.service.ApiStoreCollectService;
import co.lq.modules.shop.service.ApiStoreServie;
import co.lq.modules.shop.web.param.HomeAdvertiseQueryParam;
import co.lq.modules.shop.web.param.StoreQueryParam;
import co.lq.modules.shop.web.vo.SellerUserQueryVo;
import co.lq.modules.shop.web.vo.StoreCollectQueryVo;
import co.lq.modules.shop.web.vo.StoreQueryVo;
import co.lq.modules.shop.web.vo.StoreVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 店铺服务实现
 *
 * @author songbin
 * @since 2020年3月12日 下午9:20:57
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiStoreServieImpl extends BaseServiceImpl<StoreMapper, Store> implements ApiStoreServie {

    private final StoreMapper                storeMapper;

    private final SystemUserMapper           systemUserMapper;

    private final StoreCatalogRelationMapper storeCatalogRelationMapper;

    private final HomeAdvertiseMapper        homeAdvertiseMapper;

    private final PasswordEncoder            passwordEncoder;

    private final RoleMapper                 roleMapper;

    private final SystemUsersRolesMapper     systemUsersRolesMapper;

    private final StoreCollectMapper         storeCollectMapper;

    private final ApiStoreCollectService     apiStoreCollectService;

    @Override
    public StoreQueryVo getStoreById(Serializable id) {
        return storeMapper.getStoreById(id);
    }

    @Override
    public StoreQueryVo getStoreByUidAndId(Serializable id, Long uid) {
        StoreQueryVo storeQueryVo = storeMapper.getStoreById(id);
        if (uid > 0) {
            //收藏
            StoreCollectQueryVo storeCollectQueryVo = apiStoreCollectService.getStoreCollectByUidAndStoreIdAndType(uid,
                    (Long) id, "collect");
            if (storeCollectQueryVo != null) {
                storeQueryVo.setShopCollect(1);
            } else {
                storeQueryVo.setShopCollect(0);
            }
        } else {
            storeQueryVo.setShopCollect(0);
        }
        return storeQueryVo;
    }

    @Override
    public IPage<StoreQueryVo> getStorePageList(StoreQueryParam storeQueryParam) {
        List<OrderItem> orderItemList = new ArrayList<>();
        if (storeQueryParam.getCollect() != null) {
            if (storeQueryParam.getCollect() == 1) {
                orderItemList.add(OrderItem.asc("collect"));
            } else if (storeQueryParam.getCollect() == 0) {
                orderItemList.add(OrderItem.desc("collect"));
            }
        }

        if (storeQueryParam.getHit() != null) {
            if (storeQueryParam.getHit() == 1) {
                orderItemList.add(OrderItem.asc("hit"));
            } else if (storeQueryParam.getHit() == 0) {
                orderItemList.add(OrderItem.desc("hit"));
            }
        }

        if (storeQueryParam.getGoodsCount() != null) {
            if (storeQueryParam.getGoodsCount() == 1) {
                orderItemList.add(OrderItem.asc("goods_count"));
            } else if (storeQueryParam.getGoodsCount() == 0) {
                orderItemList.add(OrderItem.desc("goods_count"));
            }
        }
        storeQueryParam.setOrders(orderItemList);
        Page page = setPageParam(storeQueryParam, OrderItem.desc("add_time"));
        IPage<StoreQueryVo> iPage = storeMapper.getStorePageList(page, storeQueryParam);
        return iPage;
    }

    @Override
    public StoreQueryVo getShopInfoByUid(StoreQueryParam storeQueryParam) {
        return storeMapper.getShopInfoByUid(storeQueryParam);
    }

    @Override
    public Store saveShop(StoreVo storeVo) {
        Store store = new Store();
        store.setRegisterType(1);
        store.setAddressDetail(storeVo.getAddressDetail());
        store.setAddressLat(store.getAddressLat());
        store.setAddressLng(storeVo.getAddressLng());
        store.setContactMobile(storeVo.getContactMobile());
        store.setContactName(storeVo.getContactName());
        store.setContactQq(storeVo.getContactQq());
        store.setContactQrcode(storeVo.getContactQrcode());
        store.setDeleted(0);
        store.setDescription(storeVo.getDescription());
        store.setLogo(storeVo.getLogo());
        store.setName(storeVo.getName());
        store.setServicePhone(storeVo.getServicePhone());
        store.setContactName(storeVo.getContactName());
        store.setUid(storeVo.getUid());
        long cur = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(cur);
        if (storeVo.getId() == null || storeVo.getId().equals(0L)) {
            long expireTime = cur + 10 * 365 * 24 * 3600 * 1000L;
            Timestamp expireTimeStamp = new Timestamp(expireTime);
            store.setTryTime(timestamp);
            store.setExpireTime(expireTimeStamp);
            store.setAddTime(timestamp);
            store.setModifyTime(timestamp);
            storeMapper.insert(store);
            storeVo.setId(store.getId());
        } else {
            store.setId(storeVo.getId());
            store.setModifyTime(timestamp);
            storeMapper.updateById(store);
        }

        return store;
    }

    @Override
    public StoreCatalogRelation saveCatalogRelation(Long storeId, Long catalogId) {
        StoreCatalogRelation storeCatalogRelation = new StoreCatalogRelation();
        storeCatalogRelation.setCatalogId(catalogId);
        storeCatalogRelation.setCatalogId(catalogId);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        storeCatalogRelation.setAddTime(timestamp);
        storeCatalogRelation.setModifyTime(timestamp);
        storeCatalogRelation.setDeleted(false);

        storeCatalogRelationMapper.insert(storeCatalogRelation);
        return null;
    }

    @Override
    public Boolean createAdminRole(StoreVo storeVo) {
        StoreQueryVo storeQueryVo = storeMapper.getStoreById(storeVo.getId());
        SellerUserQueryVo systemUser = systemUserMapper.getSellerUserById(storeVo.getUid());

        QueryWrapper<SystemUser> wrapper = new QueryWrapper<>();
        wrapper.eq("id", storeVo.getUid());
        SystemUser systemUser1 = new SystemUser();
        systemUser1.setStoreId(storeVo.getId());
        systemUserMapper.update(systemUser1, wrapper);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Role role = new Role();
        role.setLevel(1);
        role.setName("超级管理员");
        role.setDataScope("全部");
        role.setCreateTime(timestamp);
        role.setStoreId(storeQueryVo.getId());
        role.setPermission("admin");
        roleMapper.insert(role);

        return true;
    }

    @Override
    public int collectStore(Long id, Long uid, Integer isChecked) {
        QueryWrapper<StoreCollect> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).eq("store_id", id).eq("type", "collect");
        StoreCollect storeCollect = storeCollectMapper.selectOne(wrapper);
        if (storeCollect == null) {
            storeCollect = new StoreCollect();
        }
        storeCollect.setUid(uid);
        storeCollect.setStoreId(id);
        storeCollect.setType("collect");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        storeCollect.setModifyTime(timestamp);
        if (isChecked == 1) {
            if (storeCollect.getId() != null) {
                storeCollect.setDeleted(false);
                storeCollectMapper.updateById(storeCollect);
            } else {
                storeCollect.setAddTime(timestamp);
                storeCollect.setDeleted(false);
                storeCollectMapper.insert(storeCollect);
            }
            return storeMapper.updateCollectById(id);
        } else {
            if (storeCollect.getId() != null) {
                storeCollect.setDeleted(true);
                storeCollectMapper.updateById(storeCollect);
            }
            return storeMapper.minusCollectById(id);
        }
    }

    @Override
    public Boolean relationAdminRole(StoreVo storeVo) {
        SystemUser systemUser = systemUserMapper.selectById(storeVo.getUid());
        SystemUsersRoles systemUsersRoles = new SystemUsersRoles();

        QueryWrapper<Role> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("store_id", storeVo.getId()).eq("permission", "admin");
        Role role = roleMapper.selectOne(wrapper1);
        systemUsersRoles.setRoleId(role.getId());
        systemUsersRoles.setUserId(systemUser.getId());
        systemUsersRolesMapper.insert(systemUsersRoles);
        return true;
    }

    @Override
    public List<StoreQueryVo> getShopList(StoreQueryParam storeQueryParam, int start, int limit) {
        return storeMapper.getShopList(storeQueryParam, start, limit);
    }

    @Override
    public StoreQueryVo getStoreHome(Long storeId, Long uid) {
        StoreQueryVo storeQueryVo = storeMapper.getStoreById(storeId);
        HomeAdvertiseQueryParam homeAdvertiseQueryParam = new HomeAdvertiseQueryParam();
        homeAdvertiseQueryParam.setStoreId(storeId);
        storeQueryVo.setHomeAdvertiseQueryVoList(
                homeAdvertiseMapper.getHomeAdvertisePageLists(homeAdvertiseQueryParam, 0, 3));

        //获取店铺关注
        storeQueryVo.setIsChecked(false);
        if (uid > 0) {
            StoreCollectQueryVo storeCollectQueryVo = apiStoreCollectService.getStoreCollectByUidAndStoreIdAndType(uid,
                    storeId, "collect");
            if (storeCollectQueryVo != null && storeCollectQueryVo.getId() > 0) {
                storeQueryVo.setIsChecked(true);
            }
        }
        return storeQueryVo;
    }

    @Override
    public SystemUser getSystemByUsername(String username) {
        QueryWrapper<SystemUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return systemUserMapper.selectOne(wrapper);
    }

    @Override
    public SystemUser saveSystemUser(SystemUser systemUser) {
        systemUserMapper.insert(systemUser);
        return systemUser;
    }
}
