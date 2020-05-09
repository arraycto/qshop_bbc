package co.lq.modules.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.StoreProduct;
import co.lq.modules.shop.service.dto.ProductFormatDTO;
import co.lq.modules.shop.service.dto.StoreProductDTO;
import co.lq.modules.shop.service.dto.StoreProductQueryCriteria;

/**
 * @author billy
 * @date 2019-10-04
 */
public interface StoreProductService {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(StoreProductQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<StoreProductDTO> queryAll(StoreProductQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    StoreProductDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    StoreProductDTO create(StoreProduct resources);

    /**
     * 保存商品属性和商品属性值
     *
     * @param productId
     * @param resources
     */
    void saveProductAttributes(Long productId, StoreProduct resources);

    /**
     * 保存关联专题
     *
     * @param productId
     * @param storeProduct
     */
    void saveProductRelationSubjects(Long productId, StoreProduct storeProduct);

    /**
     * 保存商品优选区
     *
     * @param productId
     * @param storeProduct
     */
    void saveProductPrefrenceAreas(Long productId, StoreProduct storeProduct);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(StoreProduct resources);

    /**
     * 删除
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);

    void recovery(Long id);

    void onSale(Long id, Integer status);

    List<ProductFormatDTO> isFormatAttr(Long id, String jsonStr);

    void createProductAttr(Long id, String jsonStr);

    void setResult(Map<String, Object> map, Long id);

    void clearProductAttr(Long id, boolean isActice);

    String getStoreProductAttrResult(Long id);

    void onVerify(Long id, Integer status);
}
