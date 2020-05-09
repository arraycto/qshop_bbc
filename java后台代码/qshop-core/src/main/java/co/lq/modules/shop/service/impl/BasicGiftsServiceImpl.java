package co.lq.modules.shop.service.impl;

import java.io.IOException;
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

import co.lq.modules.shop.domain.BasicGifts;
import co.lq.modules.shop.repository.BasicGiftsRepository;
import co.lq.modules.shop.service.BasicGiftsService;
import co.lq.modules.shop.service.dto.BasicGiftsDTO;
import co.lq.modules.shop.service.dto.BasicGiftsQueryCriteria;
import co.lq.modules.shop.service.mapper.BasicGiftsMapper;
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
 * @date 2020-03-27
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class BasicGiftsServiceImpl implements BasicGiftsService {

    private final BasicGiftsRepository basicGiftsRepository;

    private final BasicGiftsMapper     basicGiftsMapper;

    public BasicGiftsServiceImpl(BasicGiftsRepository basicGiftsRepository, BasicGiftsMapper basicGiftsMapper) {
        this.basicGiftsRepository = basicGiftsRepository;
        this.basicGiftsMapper = basicGiftsMapper;
    }

    @Override
    public Map<String, Object> queryAll(BasicGiftsQueryCriteria criteria, Pageable pageable) {
        Page<BasicGifts> page = basicGiftsRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(basicGiftsMapper::toDto));
    }

    @Override
    public List<BasicGiftsDTO> queryAll(BasicGiftsQueryCriteria criteria) {
        return basicGiftsMapper.toDto(basicGiftsRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public BasicGiftsDTO findById(Long storeId) {
        BasicGifts basicGifts = basicGiftsRepository.findById(storeId).orElseGet(BasicGifts::new);
        ValidationUtil.isNull(basicGifts.getStoreId(), "BasicGifts", "storeId", storeId);
        return basicGiftsMapper.toDto(basicGifts);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BasicGiftsDTO create(BasicGifts resources) {
        return basicGiftsMapper.toDto(basicGiftsRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BasicGifts resources) {
        BasicGifts basicGifts = basicGiftsRepository.findById(resources.getStoreId()).orElseGet(BasicGifts::new);
        ValidationUtil.isNull(basicGifts.getStoreId(), "BasicGifts", "id", resources.getStoreId());
        basicGifts.copy(resources);
        basicGiftsRepository.save(basicGifts);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            basicGiftsRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BasicGiftsDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BasicGiftsDTO basicGifts : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(" name", basicGifts.getName());
            map.put("1 有效2 无效", basicGifts.getStatus());
            map.put("活动对象1全部用户2 会员级别", basicGifts.getActiviUser());
            map.put("活动商品", basicGifts.getActiviGoods());
            map.put("1 首购礼 2 满 购礼 3 单品礼赠", basicGifts.getBigType());
            map.put("首购礼 1第一单获取 2所有订单获取 ； 满购礼1选赠礼 获取规则 2满赠礼", basicGifts.getSmallType());
            map.put("规则", basicGifts.getRules());
            map.put("部分商品列表", basicGifts.getGoodsIds());
            map.put("会员级别", basicGifts.getUserLevel());
            map.put(" startTime", basicGifts.getStartTime());
            map.put(" endTime", basicGifts.getEndTime());
            map.put("赠品", basicGifts.getGiftIds());
            map.put("添加时间", basicGifts.getAddTime());
            map.put("更新时间", basicGifts.getModifyTime());
            map.put("逻辑删除", basicGifts.getDeleted());
            map.put(" note", basicGifts.getNote());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
