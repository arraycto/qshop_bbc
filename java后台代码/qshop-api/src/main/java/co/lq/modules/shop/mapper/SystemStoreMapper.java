package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.SystemStore;
import co.lq.modules.shop.web.param.SystemStoreQueryParam;
import co.lq.modules.shop.web.vo.SystemStoreQueryVo;

/**
 * <p>
 * 门店自提 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2020-03-04
 */
@Repository
public interface SystemStoreMapper extends BaseMapper<SystemStore> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SystemStoreQueryVo getSystemStoreById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param systemStoreQueryParam
     * @return
     */
    IPage<SystemStoreQueryVo> getSystemStorePageList(@Param("page") Page page,
                                                     @Param("param") SystemStoreQueryParam systemStoreQueryParam);

}
