package co.lq.modules.activity.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.activity.entity.StoreBargain;
import co.lq.modules.activity.web.dto.BargainCountDTO;
import co.lq.modules.activity.web.dto.BargainDTO;
import co.lq.modules.activity.web.dto.TopCountDTO;
import co.lq.modules.activity.web.param.StoreBargainQueryParam;
import co.lq.modules.activity.web.vo.StoreBargainQueryVo;

/**
 * <p>
 * 砍价表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-12-21
 */
public interface ApiStoreBargainService extends BaseService<StoreBargain> {

    void incStockDecSales(int num, long bargainId);

    void decStockIncSales(int num, long bargainId);

    StoreBargain getBargain(long bargainId);

    void doHelp(long bargainId, long bargainUserUid, long uid);

    TopCountDTO topCount(long bargainId);

    BargainCountDTO helpCount(long bargainId, long uid);

    int getBargainPayCount(long bargainId);

    void addBargainShare(long id);

    void addBargainLook(long id);

    BargainDTO getDetail(long id, long uid);

    List<StoreBargainQueryVo> getList(int page, int limit);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreBargainQueryVo getStoreBargainById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param storeBargainQueryParam
     * @return
     */
    Paging<StoreBargainQueryVo> getStoreBargainPageList(StoreBargainQueryParam storeBargainQueryParam) throws Exception;

}
