package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.StoreGifts;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.StoreGiftsRepository;
import co.lq.modules.shop.service.StoreGiftsService;
import co.lq.modules.shop.service.dto.StoreGiftsDTO;
import co.lq.modules.shop.service.dto.StoreGiftsQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreGiftsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author billy
* @date 2020-03-27
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreGiftsServiceImpl implements StoreGiftsService {

    private final StoreGiftsRepository storeGiftsRepository;

    private final StoreGiftsMapper storeGiftsMapper;

    public StoreGiftsServiceImpl(StoreGiftsRepository storeGiftsRepository, StoreGiftsMapper storeGiftsMapper) {
        this.storeGiftsRepository = storeGiftsRepository;
        this.storeGiftsMapper = storeGiftsMapper;
    }

    @Override
    public Map<String,Object> queryAll(StoreGiftsQueryCriteria criteria, Pageable pageable){
        Page<StoreGifts> page = storeGiftsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(storeGiftsMapper::toDto));
    }

    @Override
    public List<StoreGiftsDTO> queryAll(StoreGiftsQueryCriteria criteria){
        return storeGiftsMapper.toDto(storeGiftsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public StoreGiftsDTO findById(Long id) {
        StoreGifts storeGifts = storeGiftsRepository.findById(id).orElseGet(StoreGifts::new);
        ValidationUtil.isNull(storeGifts.getId(),"StoreGifts","id",id);
        return storeGiftsMapper.toDto(storeGifts);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreGiftsDTO create(StoreGifts resources) {
        return storeGiftsMapper.toDto(storeGiftsRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreGifts resources) {
        StoreGifts storeGifts = storeGiftsRepository.findById(resources.getId()).orElseGet(StoreGifts::new);
        ValidationUtil.isNull( storeGifts.getId(),"StoreGifts","id",resources.getId());
        storeGifts.copy(resources);
        storeGiftsRepository.save(storeGifts);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            storeGiftsRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StoreGiftsDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StoreGiftsDTO storeGifts : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("类别", storeGifts.getCategoryId());
            map.put("图片", storeGifts.getIcon());
            map.put("标题", storeGifts.getTitle());
            map.put("状态", storeGifts.getShowStatus());
            map.put("内容", storeGifts.getContent());
            map.put("所属店铺", storeGifts.getStoreId());
            map.put("1 赠品 2 活动商品", storeGifts.getType());
            map.put(" price",  storeGifts.getPrice());
            map.put(" stock",  storeGifts.getStock());
            map.put("添加时间", storeGifts.getAddTime());
            map.put("更新时间", storeGifts.getModifyTime());
            map.put("逻辑删除", storeGifts.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}