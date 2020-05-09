package co.lq.modules.activity.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.activity.entity.StoreBargainUser;
import co.lq.modules.activity.web.param.StoreBargainUserQueryParam;
import co.lq.modules.activity.web.vo.StoreBargainUserQueryVo;

/**
 * <p>
 * 用户参与砍价表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-12-21
 */
@Repository
public interface StoreBargainUserMapper extends BaseMapper<StoreBargainUser> {

    @Select("SELECT u.uid,u.is_del as isDel,u.bargain_price - u.price as residuePrice,u.id,"
            + "u.bargain_id as bargainId,u.bargain_price as bargainPrice,"
            + "u.bargain_price_min as bargainPriceMin,u.price,u.status,b.title,"
            + "b.image,b.stop_time as datatime FROM store_bargain_user u INNER JOIN "
            + "store_bargain b ON b.id=u.bargain_id WHERE u.uid = #{uid} AND u.is_del = 0 " + "ORDER BY u.id DESC ")
    List<StoreBargainUserQueryVo> getBargainUserList(@Param("uid") long uid, Page page);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreBargainUserQueryVo getStoreBargainUserById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeBargainUserQueryParam
     * @return
     */
    IPage<StoreBargainUserQueryVo> getStoreBargainUserPageList(@Param("page") Page page,
                                                               @Param("param") StoreBargainUserQueryParam storeBargainUserQueryParam);

}
