package co.lq.modules.activity.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.activity.entity.StorePink;
import co.lq.modules.activity.web.dto.PinkDTO;
import co.lq.modules.activity.web.dto.PinkInfoDTO;
import co.lq.modules.activity.web.param.StorePinkQueryParam;
import co.lq.modules.activity.web.vo.StorePinkQueryVo;
import co.lq.modules.order.web.vo.StoreOrderQueryVo;

/**
 * <p>
 * 拼团表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-11-19
 */
public interface ApiStorePinkService extends BaseService<StorePink> {

    void orderPinkFailAfter(long uid, long pid);

    void removePink(long uid, long cid, long pinkId);

    int surplusPeople(StorePink pink);

    List<StorePinkQueryVo> handPinkAll(List<StorePink> pinkAll);

    StorePinkQueryVo handPinkT(StorePink pinkT);

    StorePink getCurrentPink(long id, long uid);

    String getCurrentPinkOrderId(long id, long uid);

    PinkInfoDTO pinkInfo(long id, long uid);

    PinkDTO getPinkUserOneT(long id);

    void setPinkStopTime(List<Long> idAll);

    boolean getPinkStatus(List<Long> idAll);

    int pinkFail(List<StorePink> pinkAll, StorePink pinkT, int pinkBool);

    int pinkComplete(List<Long> uidAll, List<Long> idAll, long uid, StorePink pinkT);

    List<StorePink> getPinkMember(long kid);

    StorePink getPinkUserOne(long id);

    Map<String, Object> getPinkMemberAndPinK(StorePink pink);

    int pinkIngCount(long id);

    void createPink(StoreOrderQueryVo order);

    int getIsPinkUid(long id, long uid);

    int getPinkOkSumTotalNum();

    List<String> getPinkOkList(long uid);

    int getPinkPeople(long kid, int people);

    Map<String, Object> getPinkAll(long cid, boolean isAll);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StorePinkQueryVo getStorePinkById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param storePinkQueryParam
     * @return
     */
    Paging<StorePinkQueryVo> getStorePinkPageList(StorePinkQueryParam storePinkQueryParam) throws Exception;

}
