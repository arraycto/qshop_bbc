package co.lq.modules.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import co.lq.modules.shop.domain.User;
import co.lq.modules.shop.service.dto.UserDTO;
import co.lq.modules.shop.service.dto.UserQueryCriteria;

/**
 * @author billy
 * @date 2019-10-06
 */
@Repository
public interface SellerUserMapper extends BaseMapper<User> {

    /**
     * 获取店铺下的会员
     *
     * @param userQueryCriteria
     * @return 返回会员结构
     */
    IPage<UserDTO> getUserPageListByShopId(@Param("page") IPage page,
                                           @Param("param") UserQueryCriteria userQueryCriteria);

    List<UserDTO> getUsersByShopId(@Param("param") UserQueryCriteria userQueryCriteria);

    Long updateBrokeragePriceByUid(@Param("brokeragePrice") double brokeragePrice, @Param("uid") long uid);

    void saveMemberLevelStore(@Param("userId") Long userId, @Param("levelId") Long levelId,
                              @Param("storeId") Long storeId);

    void delteMemberLevelStoreByUserId(@Param("userId") Long userId, @Param("storeId") Long storeId);
}
