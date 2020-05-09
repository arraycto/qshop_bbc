package co.lq.modules.shop.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.HomeNewProduct;
import co.lq.modules.shop.service.dto.HomeNewProductDTO;
import co.lq.modules.shop.service.dto.HomeNewProductQueryCriteria;

/**
 * @author billy
 * @date 2020-03-27
 */
public interface HomeNewProductService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String, Object> queryAll(HomeNewProductQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<HomeNewProductDTO>
     */
    List<HomeNewProductDTO> queryAll(HomeNewProductQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return HomeNewProductDTO
     */
    HomeNewProductDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return HomeNewProductDTO
     */
    List<HomeNewProductDTO> create(List<HomeNewProduct> resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(HomeNewProduct resources);

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
    void download(List<HomeNewProductDTO> all, HttpServletResponse response) throws IOException;
}
