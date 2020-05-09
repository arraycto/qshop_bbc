package co.lq.modules.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.modules.shop.entity.SystemGroupData;
import co.lq.modules.shop.mapper.SystemGroupDataMapper;
import co.lq.modules.shop.service.ApiSystemGroupDataService;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 组合数据详情表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-19
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiSystemGroupDataServiceImpl extends BaseServiceImpl<SystemGroupDataMapper, SystemGroupData>
        implements ApiSystemGroupDataService {

    /**
     * 获取配置数据
     *
     * @param name
     * @return
     */
    @Override
    public List<Map<String, Object>> getDatas(String name) {
        QueryWrapper<SystemGroupData> wrapper = new QueryWrapper<>();

        List<Map<String, Object>> list = new ArrayList<>();

        wrapper.eq("group_name", name).eq("status", 1).orderByDesc("sort");
        List<SystemGroupData> systemGroupDatas = baseMapper.selectList(wrapper);

        for (SystemGroupData systemGroupData : systemGroupDatas) {
            list.add(JSONObject.parseObject(systemGroupData.getValue()));
        }

        return list;
    }

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    @Override
    public SystemGroupData findData(Integer id) {
        return baseMapper.selectById(id);
    }
}
