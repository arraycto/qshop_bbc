package co.lq.modules.shop.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.HomeAdvertise;
import co.lq.modules.shop.web.param.HomeAdvertiseQueryParam;
import co.lq.modules.shop.web.vo.HomeAdvertiseQueryVo;

/**
 * 广告
 *
 * @author songbin
 * @since 2020年3月13日 下午10:38:26
 */
public interface ApiHomeAdvertiseService extends BaseService<HomeAdvertise> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    HomeAdvertiseQueryVo getAdvertiseById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param homeAdvertiseQueryParam
     * @return
     */
    Paging<HomeAdvertiseQueryVo> getAdvertisePageList(HomeAdvertiseQueryParam homeAdvertiseQueryParam);

}
