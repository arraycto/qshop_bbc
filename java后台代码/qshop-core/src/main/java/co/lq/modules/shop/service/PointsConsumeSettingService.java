package co.lq.modules.shop.service;

import co.lq.modules.shop.domain.PointsConsumeSetting;
import co.lq.modules.shop.service.dto.PointsConsumeSettingDTO;
import co.lq.modules.shop.service.dto.PointsConsumeSettingQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author billy
* @date 2020-03-27
*/
public interface PointsConsumeSettingService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(PointsConsumeSettingQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<PointsConsumeSettingDTO>
    */
    List<PointsConsumeSettingDTO> queryAll(PointsConsumeSettingQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return PointsConsumeSettingDTO
     */
    PointsConsumeSettingDTO findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return PointsConsumeSettingDTO
    */
    PointsConsumeSettingDTO create(PointsConsumeSetting resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(PointsConsumeSetting resources);

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
    void download(List<PointsConsumeSettingDTO> all, HttpServletResponse response) throws IOException;
}