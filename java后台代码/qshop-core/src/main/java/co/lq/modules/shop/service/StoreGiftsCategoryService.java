package co.lq.modules.shop.service;

import co.lq.modules.shop.domain.StoreGiftsCategory;
import co.lq.modules.shop.service.dto.StoreGiftsCategoryDTO;
import co.lq.modules.shop.service.dto.StoreGiftsCategoryQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author billy
* @date 2020-03-27
*/
public interface StoreGiftsCategoryService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(StoreGiftsCategoryQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<StoreGiftsCategoryDTO>
    */
    List<StoreGiftsCategoryDTO> queryAll(StoreGiftsCategoryQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return StoreGiftsCategoryDTO
     */
    StoreGiftsCategoryDTO findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return StoreGiftsCategoryDTO
    */
    StoreGiftsCategoryDTO create(StoreGiftsCategory resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(StoreGiftsCategory resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<StoreGiftsCategoryDTO> all, HttpServletResponse response) throws IOException;
}