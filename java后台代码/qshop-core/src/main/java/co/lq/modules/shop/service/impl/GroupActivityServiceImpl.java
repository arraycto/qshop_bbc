package co.lq.modules.shop.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.shop.domain.GroupActivity;
import co.lq.modules.shop.repository.GroupActivityRepository;
import co.lq.modules.shop.service.GroupActivityService;
import co.lq.modules.shop.service.dto.GroupActivityDTO;
import co.lq.modules.shop.service.dto.GroupActivityQueryCriteria;
import co.lq.modules.shop.service.mapper.GroupActivityMapper;
import co.lq.utils.FileUtil;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
 * @author billy
 * @date 2020-04-02
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GroupActivityServiceImpl implements GroupActivityService {

    private final GroupActivityRepository groupActivityRepository;

    private final GroupActivityMapper     groupActivityMapper;

    public GroupActivityServiceImpl(GroupActivityRepository groupActivityRepository,
                                    GroupActivityMapper groupActivityMapper) {
        this.groupActivityRepository = groupActivityRepository;
        this.groupActivityMapper = groupActivityMapper;
    }

    @Override
    public Map<String, Object> queryAll(GroupActivityQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<GroupActivity> page = groupActivityRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(groupActivityMapper::toDto));
    }

    @Override
    public List<GroupActivityDTO> queryAll(GroupActivityQueryCriteria criteria) {
        criteria.setDeleted(0);
        return groupActivityMapper.toDto(groupActivityRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public GroupActivityDTO findById(Long id) {
        GroupActivity groupActivity = groupActivityRepository.findById(id).orElseGet(GroupActivity::new);
        ValidationUtil.isNull(groupActivity.getId(), "GroupActivity", "id", id);
        return groupActivityMapper.toDto(groupActivity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GroupActivityDTO create(GroupActivity resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return groupActivityMapper.toDto(groupActivityRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GroupActivity resources) {
        GroupActivity groupActivity = groupActivityRepository.findById(resources.getId()).orElseGet(GroupActivity::new);
        ValidationUtil.isNull(groupActivity.getId(), "GroupActivity", "id", resources.getId());
        groupActivity.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        groupActivity.setModifyTime(timestamp);
        groupActivityRepository.save(groupActivity);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            GroupActivity groupActivity = groupActivityRepository.findById(id).orElseGet(GroupActivity::new);
            ValidationUtil.isNull(groupActivity.getId(), "GroupActivity", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            groupActivity.setModifyTime(timestamp);
            groupActivity.setDeleted(1);
            groupActivityRepository.save(groupActivity);
        }
    }

    @Override
    public void download(List<GroupActivityDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (GroupActivityDTO smsGroupActivity : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("活动名称", smsGroupActivity.getName());
            map.put("活动价格", smsGroupActivity.getPrice());
            map.put("运费", smsGroupActivity.getTransfee());
            map.put("活动状态 1 开启 2 关闭", smsGroupActivity.getStatus());
            map.put("1 买家承担 2 卖家承担", smsGroupActivity.getFeestatus());
            map.put("所属店铺", smsGroupActivity.getStoreId());
            map.put(" goodsIds", smsGroupActivity.getGoodsIds());
            map.put(" pic", smsGroupActivity.getPic());
            map.put(" originprice", smsGroupActivity.getOriginprice());
            map.put("添加时间", smsGroupActivity.getAddTime());
            map.put("更新时间", smsGroupActivity.getModifyTime());
            map.put("逻辑删除", smsGroupActivity.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
