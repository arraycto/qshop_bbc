package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.HomeBrand;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.HomeBrandRepository;
import co.lq.modules.shop.service.HomeBrandService;
import co.lq.modules.shop.service.dto.HomeBrandDTO;
import co.lq.modules.shop.service.dto.HomeBrandQueryCriteria;
import co.lq.modules.shop.service.mapper.HomeBrandMapper;
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
public class HomeBrandServiceImpl implements HomeBrandService {

    private final HomeBrandRepository homeBrandRepository;

    private final HomeBrandMapper homeBrandMapper;

    public HomeBrandServiceImpl(HomeBrandRepository homeBrandRepository, HomeBrandMapper homeBrandMapper) {
        this.homeBrandRepository = homeBrandRepository;
        this.homeBrandMapper = homeBrandMapper;
    }

    @Override
    public Map<String,Object> queryAll(HomeBrandQueryCriteria criteria, Pageable pageable){
        Page<HomeBrand> page = homeBrandRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(homeBrandMapper::toDto));
    }

    @Override
    public List<HomeBrandDTO> queryAll(HomeBrandQueryCriteria criteria){
        return homeBrandMapper.toDto(homeBrandRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public HomeBrandDTO findById(Long id) {
        HomeBrand homeBrand = homeBrandRepository.findById(id).orElseGet(HomeBrand::new);
        ValidationUtil.isNull(homeBrand.getId(),"HomeBrand","id",id);
        return homeBrandMapper.toDto(homeBrand);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HomeBrandDTO create(HomeBrand resources) {
        return homeBrandMapper.toDto(homeBrandRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HomeBrand resources) {
        HomeBrand homeBrand = homeBrandRepository.findById(resources.getId()).orElseGet(HomeBrand::new);
        ValidationUtil.isNull( homeBrand.getId(),"HomeBrand","id",resources.getId());
        homeBrand.copy(resources);
        homeBrandRepository.save(homeBrand);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            homeBrandRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<HomeBrandDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (HomeBrandDTO homeBrand : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" brandId",  homeBrand.getBrandId());
            map.put(" brandName",  homeBrand.getBrandName());
            map.put(" recommendStatus",  homeBrand.getRecommendStatus());
            map.put(" sort",  homeBrand.getSort());
            map.put("所属店铺", homeBrand.getStoreId());
            map.put("添加时间", homeBrand.getAddTime());
            map.put("更新时间", homeBrand.getModifyTime());
            map.put("逻辑删除", homeBrand.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}