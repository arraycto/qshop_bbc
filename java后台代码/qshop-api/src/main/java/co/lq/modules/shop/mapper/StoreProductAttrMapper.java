package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.StoreProductAttr;
import co.lq.modules.shop.web.param.StoreProductAttrQueryParam;
import co.lq.modules.shop.web.vo.StoreProductAttrQueryVo;

/**
 * <p>
 * 商品属性表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
@Repository
public interface StoreProductAttrMapper extends BaseMapper<StoreProductAttr> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreProductAttrQueryVo getStoreProductAttrById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeProductAttrQueryParam
     * @return
     */
    IPage<StoreProductAttrQueryVo> getStoreProductAttrPageList(@Param("page") Page page,
                                                               @Param("param") StoreProductAttrQueryParam storeProductAttrQueryParam);

}
