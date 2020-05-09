package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.Category;
import co.lq.modules.shop.web.param.CategoryQueryParam;
import co.lq.modules.shop.web.vo.CategoryQueryVo;

/**
 * <p>
 * 商品分类表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-22
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CategoryQueryVo getStoreCategoryById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param categoryQueryParam
     * @return
     */
    IPage<CategoryQueryVo> getStoreCategoryPageList(@Param("page") Page page,
                                                    @Param("param") CategoryQueryParam categoryQueryParam);

}
