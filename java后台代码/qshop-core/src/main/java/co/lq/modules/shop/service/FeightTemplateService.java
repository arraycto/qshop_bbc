package co.lq.modules.shop.service;

import co.lq.modules.shop.domain.FeightTemplate;
import co.lq.modules.shop.service.dto.FeightTemplateDTO;
import co.lq.modules.shop.service.dto.FeightTemplateQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author billy
* @date 2020-03-27
*/
public interface FeightTemplateService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(FeightTemplateQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<FeightTemplateDTO>
    */
    List<FeightTemplateDTO> queryAll(FeightTemplateQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return FeightTemplateDTO
     */
    FeightTemplateDTO findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return FeightTemplateDTO
    */
    FeightTemplateDTO create(FeightTemplate resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(FeightTemplate resources);

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
    void download(List<FeightTemplateDTO> all, HttpServletResponse response) throws IOException;
}