package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.StoreGiftsCategory;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.StoreGiftsCategoryRepository;
import co.lq.modules.shop.service.StoreGiftsCategoryService;
import co.lq.modules.shop.service.dto.StoreGiftsCategoryDTO;
import co.lq.modules.shop.service.dto.StoreGiftsCategoryQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreGiftsCategoryMapper;
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
public class StoreGiftsCategoryServiceImpl implements StoreGiftsCategoryService {

    private final StoreGiftsCategoryRepository storeGiftsCategoryRepository;

    private final StoreGiftsCategoryMapper storeGiftsCategoryMapper;

    public StoreGiftsCategoryServiceImpl(StoreGiftsCategoryRepository storeGiftsCategoryRepository, StoreGiftsCategoryMapper storeGiftsCategoryMapper) {
        this.storeGiftsCategoryRepository = storeGiftsCategoryRepository;
        this.storeGiftsCategoryMapper = storeGiftsCategoryMapper;
    }

    @Override
    public Map<String,Object> queryAll(StoreGiftsCategoryQueryCriteria criteria, Pageable pageable){
        Page<StoreGiftsCategory> page = storeGiftsCategoryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(storeGiftsCategoryMapper::toDto));
    }

    @Override
    public List<StoreGiftsCategoryDTO> queryAll(StoreGiftsCategoryQueryCriteria criteria){
        return storeGiftsCategoryMapper.toDto(storeGiftsCategoryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public StoreGiftsCategoryDTO findById(Long id) {
        StoreGiftsCategory storeGiftsCategory = storeGiftsCategoryRepository.findById(id).orElseGet(StoreGiftsCategory::new);
        ValidationUtil.isNull(storeGiftsCategory.getId(),"StoreGiftsCategory","id",id);
        return storeGiftsCategoryMapper.toDto(storeGiftsCategory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreGiftsCategoryDTO create(StoreGiftsCategory resources) {
        return storeGiftsCategoryMapper.toDto(storeGiftsCategoryRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreGiftsCategory resources) {
        StoreGiftsCategory storeGiftsCategory = storeGiftsCategoryRepository.findById(resources.getId()).orElseGet(StoreGiftsCategory::new);
        ValidationUtil.isNull( storeGiftsCategory.getId(),"StoreGiftsCategory","id",resources.getId());
        storeGiftsCategory.copy(resources);
        storeGiftsCategoryRepository.save(storeGiftsCategory);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            storeGiftsCategoryRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StoreGiftsCategoryDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StoreGiftsCategoryDTO storeGiftsCategory : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("标题", storeGiftsCategory.getName());
            map.put("分类图标", storeGiftsCategory.getIcon());
            map.put("状态", storeGiftsCategory.getShowStatus());
            map.put("排序", storeGiftsCategory.getSort());
            map.put("所属店铺", storeGiftsCategory.getStoreId());
            map.put("添加时间", storeGiftsCategory.getAddTime());
            map.put("更新时间", storeGiftsCategory.getModifyTime());
            map.put("逻辑删除", storeGiftsCategory.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}