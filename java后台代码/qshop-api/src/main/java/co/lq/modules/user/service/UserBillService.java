package co.lq.modules.user.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.UserBill;
import co.lq.modules.user.web.dto.BillDTO;
import co.lq.modules.user.web.param.UserBillQueryParam;
import co.lq.modules.user.web.vo.UserBillQueryVo;

/**
 * <p>
 * 用户账单表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
public interface UserBillService extends BaseService<UserBill> {

    int cumulativeAttendance(long uid);

    Map<String, Object> spreadOrder(long uid, int page, int limit);

    List<BillDTO> getUserBillList(int page, int limit, long uid, int type);

    double getBrokerage(long uid);

    double yesterdayCommissionSum(long uid);

    List<UserBillQueryVo> userBillList(long uid, int page, int limit, String category);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserBillQueryVo getUserBillById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param userBillQueryParam
     * @return
     */
    Paging<UserBillQueryVo> getUserBillPageList(UserBillQueryParam userBillQueryParam) throws Exception;

}
