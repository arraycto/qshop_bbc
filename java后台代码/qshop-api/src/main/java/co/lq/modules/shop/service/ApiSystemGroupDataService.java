package co.lq.modules.shop.service;

import java.util.List;
import java.util.Map;

import co.lq.common.service.BaseService;
import co.lq.modules.shop.entity.SystemGroupData;

/**
 * <p>
 * 组合数据详情表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-19
 */
public interface ApiSystemGroupDataService extends BaseService<SystemGroupData> {

    List<Map<String, Object>> getDatas(String name);

    SystemGroupData findData(Integer id);

}
