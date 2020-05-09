package co.lq.modules.shop.service;

import co.lq.modules.shop.domain.StoreBrand;
import co.lq.modules.shop.service.dto.StoreBrandDTO;
import co.lq.modules.shop.service.dto.StoreBrandQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author billy
* @date 2020-03-27
*/
public interface StoreBrandService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(StoreBrandQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<StoreBrandDTO>
    */
    List<StoreBrandDTO> queryAll(StoreBrandQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return StoreBrandDTO
     */
    StoreBrandDTO findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return StoreBrandDTO
    */
    StoreBrandDTO create(StoreBrand resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(StoreBrand resources);

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
    void download(List<StoreBrandDTO> all, HttpServletResponse response) throws IOException;
}