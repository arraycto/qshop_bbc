package co.lq.modules.shop.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.modules.shop.entity.SystemConfig;
import co.lq.modules.shop.mapper.SystemConfigMapper;
import co.lq.modules.shop.service.ApiSystemConfigService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 配置表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-19
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiSystemConfigServiceImpl extends BaseServiceImpl<SystemConfigMapper, SystemConfig>
        implements ApiSystemConfigService {

    private final SystemConfigMapper systemConfigMapper;

    @Override
    public String getData(String name) {
        QueryWrapper<SystemConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("menu_name", name);
        SystemConfig systemConfig = systemConfigMapper.selectOne(wrapper);
        if (systemConfig == null)
            return "";
        return systemConfig.getValue();
    }
}
