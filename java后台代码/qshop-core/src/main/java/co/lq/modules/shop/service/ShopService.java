package co.lq.modules.shop.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.Shop;
import co.lq.modules.shop.service.dto.ShopDTO;
import co.lq.modules.shop.service.dto.ShopQueryCriteria;

/**
 * @author billy
 * @date 2020-03-10
 */
public interface ShopService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String, Object> queryAll(ShopQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<QshopStoreDto>
     */
    List<ShopDTO> queryAll(ShopQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return QshopStoreDto
     */
    Shop findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return QshopStoreDto
     */
    ShopDTO create(Shop resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Shop resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Long[] ids);

    /**
     * 导出数据
     *
     * @param all 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<ShopDTO> all, HttpServletResponse response) throws IOException;
}
