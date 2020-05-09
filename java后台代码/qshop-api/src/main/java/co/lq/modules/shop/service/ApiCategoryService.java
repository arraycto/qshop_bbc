package co.lq.modules.shop.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.modules.shop.entity.Category;
import co.lq.modules.shop.web.vo.CategoryQueryVo;
import co.lq.utils.CateDTO;

/**
 * <p>
 * 商品分类表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-22
 */
public interface ApiCategoryService extends BaseService<Category> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CategoryQueryVo getStoreCategoryById(Serializable id) throws Exception;

    List<CateDTO> getList();

    List<String> getAllChilds(int catid);

}
