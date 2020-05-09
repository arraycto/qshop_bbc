package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.StoreCollect;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.StoreCollectRepository;
import co.lq.modules.shop.service.StoreCollectService;
import co.lq.modules.shop.service.dto.StoreCollectDTO;
import co.lq.modules.shop.service.dto.StoreCollectQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreCollectMapper;
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
* @date 2020-04-05
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreCollectServiceImpl implements StoreCollectService {

    private final StoreCollectRepository storeCollectRepository;

    private final StoreCollectMapper storeCollectMapper;

    public StoreCollectServiceImpl(StoreCollectRepository storeCollectRepository, StoreCollectMapper storeCollectMapper) {
        this.storeCollectRepository = storeCollectRepository;
        this.storeCollectMapper = storeCollectMapper;
    }

    @Override
    public Map<String,Object> queryAll(StoreCollectQueryCriteria criteria, Pageable pageable){
        Page<StoreCollect> page = storeCollectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(storeCollectMapper::toDto));
    }

    @Override
    public List<StoreCollectDTO> queryAll(StoreCollectQueryCriteria criteria){
        return storeCollectMapper.toDto(storeCollectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public StoreCollectDTO findById(Long id) {
        StoreCollect storeCollect = storeCollectRepository.findById(id).orElseGet(StoreCollect::new);
        ValidationUtil.isNull(storeCollect.getId(),"StoreCollect","id",id);
        return storeCollectMapper.toDto(storeCollect);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreCollectDTO create(StoreCollect resources) {
        return storeCollectMapper.toDto(storeCollectRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreCollect resources) {
        StoreCollect storeCollect = storeCollectRepository.findById(resources.getId()).orElseGet(StoreCollect::new);
        ValidationUtil.isNull( storeCollect.getId(),"StoreCollect","id",resources.getId());
        storeCollect.copy(resources);
        storeCollectRepository.save(storeCollect);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            storeCollectRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StoreCollectDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StoreCollectDTO storeCollect : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户ID", storeCollect.getUid());
            map.put("店铺ID", storeCollect.getStoreId());
            map.put("类型(收藏(collect）、点赞(like))", storeCollect.getType());
            map.put("添加时间", storeCollect.getAddTime());
            map.put("更新时间", storeCollect.getModifyTime());
            map.put("逻辑删除", storeCollect.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}