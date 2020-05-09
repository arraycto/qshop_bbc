package co.lq.modules.shop.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.HomeAdvertise;
import co.lq.modules.shop.service.dto.HomeAdvertiseDTO;
import co.lq.modules.shop.service.dto.HomeAdvertiseQueryCriteria;

/**
 * @author billy
 * @date 2020-03-13
 */
public interface HomeAdvertiseService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String, Object> queryAll(HomeAdvertiseQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<HomeAdvertiseDTO>
     */
    List<HomeAdvertiseDTO> queryAll(HomeAdvertiseQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return HomeAdvertiseDTO
     */
    HomeAdvertiseDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return HomeAdvertiseDTO
     */
    HomeAdvertiseDTO create(HomeAdvertise resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(HomeAdvertise resources);

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
    void download(List<HomeAdvertiseDTO> all, HttpServletResponse response) throws IOException;
}
