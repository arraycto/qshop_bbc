package co.lq.modules.shop.service;

import co.lq.common.service.BaseService;
import co.lq.modules.shop.entity.SystemConfig;

/**
 * <p>
 * 配置表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-19
 */
public interface ApiSystemConfigService extends BaseService<SystemConfig> {

    String getData(String name);
}
