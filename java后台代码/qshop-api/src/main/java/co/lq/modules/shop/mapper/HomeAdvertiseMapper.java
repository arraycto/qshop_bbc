package co.lq.modules.shop.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.HomeAdvertise;
import co.lq.modules.shop.web.param.HomeAdvertiseQueryParam;
import co.lq.modules.shop.web.vo.HomeAdvertiseQueryVo;

/**
 * 广告
 *
 * @author songbin
 * @since 2020年3月13日 下午10:12:47
 */
public interface HomeAdvertiseMapper extends BaseMapper<HomeAdvertise> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    HomeAdvertiseQueryVo getAdvertiseById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param homeAdvertiseQueryParam
     * @return
     */
    IPage<HomeAdvertiseQueryVo> getHomeAdvertisePageList(@Param("page") Page page,
                                                         @Param("param") HomeAdvertiseQueryParam homeAdvertiseQueryParam);

    /**
     * 获取分页对象
     *
     * @param homeAdvertiseQueryParam
     * @param limit
     * @param start
     * @return
     */
    List<HomeAdvertiseQueryVo> getHomeAdvertisePageLists(@Param("param") HomeAdvertiseQueryParam homeAdvertiseQueryParam,
                                                         @Param("start") int start, @Param("limit") int limit);
}
