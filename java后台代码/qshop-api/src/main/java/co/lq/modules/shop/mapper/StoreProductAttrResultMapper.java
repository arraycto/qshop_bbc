package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.StoreProductAttrResult;
import co.lq.modules.shop.web.param.StoreProductAttrResultQueryParam;
import co.lq.modules.shop.web.vo.StoreProductAttrResultQueryVo;

/**
 * <p>
 * 商品属性详情表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
@Repository
public interface StoreProductAttrResultMapper extends BaseMapper<StoreProductAttrResult> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreProductAttrResultQueryVo getStoreProductAttrResultById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeProductAttrResultQueryParam
     * @return
     */
    IPage<StoreProductAttrResultQueryVo> getStoreProductAttrResultPageList(@Param("page") Page page,
                                                                           @Param("param") StoreProductAttrResultQueryParam storeProductAttrResultQueryParam);

}
