package co.lq.modules.shop.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import co.lq.modules.shop.entity.StoreCatalogRelation;
import co.lq.modules.shop.web.param.StoreCatalogRelationQueryParam;
import co.lq.modules.shop.web.vo.StoreCatalogRelationQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <p>
 * 店铺类目关联表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2020-04-23
 */
@Repository
public interface StoreCatalogRelationMapper extends BaseMapper<StoreCatalogRelation> {

    /**
     * 根据ID获取查询对象
     * @param id
     * @return
     */
    StoreCatalogRelationQueryVo getStoreCatalogRelationById(Serializable id);

    /**
     * 获取分页对象
     * @param page
     * @param storeCatalogRelationQueryParam
     * @return
     */
    IPage<StoreCatalogRelationQueryVo> getStoreCatalogRelationPageList(@Param("page") Page page, @Param("param") StoreCatalogRelationQueryParam storeCatalogRelationQueryParam);

}
