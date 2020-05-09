package co.lq.modules.shop.service;

import co.lq.modules.shop.domain.CmsSubjectProductRelation;
import co.lq.modules.shop.service.dto.CmsSubjectProductRelationDTO;
import co.lq.modules.shop.service.dto.CmsSubjectProductRelationQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author billy
* @date 2020-03-27
*/
public interface CmsSubjectProductRelationService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(CmsSubjectProductRelationQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<CmsSubjectProductRelationDTO>
    */
    List<CmsSubjectProductRelationDTO> queryAll(CmsSubjectProductRelationQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return CmsSubjectProductRelationDTO
     */
    CmsSubjectProductRelationDTO findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return CmsSubjectProductRelationDTO
    */
    CmsSubjectProductRelationDTO create(CmsSubjectProductRelation resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(CmsSubjectProductRelation resources);

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
    void download(List<CmsSubjectProductRelationDTO> all, HttpServletResponse response) throws IOException;
}