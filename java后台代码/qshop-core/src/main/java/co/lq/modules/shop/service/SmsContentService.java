package co.lq.modules.shop.service;

import co.lq.modules.shop.domain.SmsContent;
import co.lq.modules.shop.service.dto.SmsContentDTO;
import co.lq.modules.shop.service.dto.SmsContentQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author billy
* @date 2020-03-27
*/
public interface SmsContentService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(SmsContentQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<SmsContentDTO>
    */
    List<SmsContentDTO> queryAll(SmsContentQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return SmsContentDTO
     */
    SmsContentDTO findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return SmsContentDTO
    */
    SmsContentDTO create(SmsContent resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(SmsContent resources);

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
    void download(List<SmsContentDTO> all, HttpServletResponse response) throws IOException;
}