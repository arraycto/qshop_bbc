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

import co.lq.modules.shop.domain.StoreWithdraw;
import co.lq.modules.shop.repository.StoreWithdrawRepository;
import co.lq.modules.shop.service.StoreWithdrawService;
import co.lq.modules.shop.service.dto.StoreWithdrawDTO;
import co.lq.modules.shop.service.dto.StoreWithdrawQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreWithdrawMapper;
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
 * @date 2020-04-13
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreWithdrawServiceImpl implements StoreWithdrawService {

    private final StoreWithdrawRepository storeWithdrawRepository;

    private final StoreWithdrawMapper     storeWithdrawMapper;

    public StoreWithdrawServiceImpl(StoreWithdrawRepository storeWithdrawRepository,
                                    StoreWithdrawMapper storeWithdrawMapper) {
        this.storeWithdrawRepository = storeWithdrawRepository;
        this.storeWithdrawMapper = storeWithdrawMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreWithdrawQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<StoreWithdraw> page = storeWithdrawRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(storeWithdrawMapper::toDto));
    }

    @Override
    public List<StoreWithdrawDTO> queryAll(StoreWithdrawQueryCriteria criteria) {
        criteria.setDeleted(0);
        return storeWithdrawMapper.toDto(storeWithdrawRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreWithdrawDTO findById(Long id) {
        StoreWithdraw storeWithdraw = storeWithdrawRepository.findById(id).orElseGet(StoreWithdraw::new);
        ValidationUtil.isNull(storeWithdraw.getId(), "StoreWithdraw", "id", id);
        return storeWithdrawMapper.toDto(storeWithdraw);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreWithdrawDTO create(StoreWithdraw resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        resources.setApplyStatus(0);
        return storeWithdrawMapper.toDto(storeWithdrawRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreWithdraw resources) {
        StoreWithdraw storeWithdraw = storeWithdrawRepository.findById(resources.getId()).orElseGet(StoreWithdraw::new);
        ValidationUtil.isNull(storeWithdraw.getId(), "StoreWithdraw", "id", resources.getId());
        storeWithdraw.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        storeWithdraw.setModifyTime(timestamp);
        storeWithdrawRepository.save(storeWithdraw);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            StoreWithdraw storeWithdraw = storeWithdrawRepository.findById(id).orElseGet(StoreWithdraw::new);
            ValidationUtil.isNull(storeWithdraw.getId(), "StoreWithdraw", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            storeWithdraw.setModifyTime(timestamp);
            storeWithdraw.setDeleted(1);
            storeWithdrawRepository.save(storeWithdraw);
        }
    }

    @Override
    public void download(List<StoreWithdrawDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StoreWithdrawDTO storeWithdraw : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("申请金额", storeWithdraw.getApplyAmount());
            map.put("审核时间", storeWithdraw.getVerifyTime());
            map.put("提现状态", storeWithdraw.getApplyStatus());
            map.put("所属店铺", storeWithdraw.getStoreId());
            map.put("添加时间", storeWithdraw.getAddTime());
            map.put("更新时间", storeWithdraw.getModifyTime());
            map.put("逻辑删除", storeWithdraw.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
