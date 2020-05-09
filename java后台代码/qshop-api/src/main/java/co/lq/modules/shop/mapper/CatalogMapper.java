package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.Catalog;
import co.lq.modules.shop.web.param.CatalogQueryParam;
import co.lq.modules.shop.web.vo.CatalogQueryVo;

/**
 * 类目
 *
 * @author songbin
 * @since 2020年3月11日 下午9:08:04
 */
@Repository
public interface CatalogMapper extends BaseMapper<Catalog> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CatalogQueryVo getCatalogById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param catalogQueryParam
     * @return
     */
    IPage<CatalogQueryVo> getCatalogPageList(@Param("page") Page page,
                                             @Param("param") CatalogQueryParam catalogQueryParam);

}
