package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.HomeRecommendSubject;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.HomeRecommendSubjectRepository;
import co.lq.modules.shop.service.HomeRecommendSubjectService;
import co.lq.modules.shop.service.dto.HomeRecommendSubjectDTO;
import co.lq.modules.shop.service.dto.HomeRecommendSubjectQueryCriteria;
import co.lq.modules.shop.service.mapper.HomeRecommendSubjectMapper;
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
public class HomeRecommendSubjectServiceImpl implements HomeRecommendSubjectService {

    private final HomeRecommendSubjectRepository homeRecommendSubjectRepository;

    private final HomeRecommendSubjectMapper homeRecommendSubjectMapper;

    public HomeRecommendSubjectServiceImpl(HomeRecommendSubjectRepository homeRecommendSubjectRepository, HomeRecommendSubjectMapper homeRecommendSubjectMapper) {
        this.homeRecommendSubjectRepository = homeRecommendSubjectRepository;
        this.homeRecommendSubjectMapper = homeRecommendSubjectMapper;
    }

    @Override
    public Map<String,Object> queryAll(HomeRecommendSubjectQueryCriteria criteria, Pageable pageable){
        Page<HomeRecommendSubject> page = homeRecommendSubjectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(homeRecommendSubjectMapper::toDto));
    }

    @Override
    public List<HomeRecommendSubjectDTO> queryAll(HomeRecommendSubjectQueryCriteria criteria){
        return homeRecommendSubjectMapper.toDto(homeRecommendSubjectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public HomeRecommendSubjectDTO findById(Long id) {
        HomeRecommendSubject homeRecommendSubject = homeRecommendSubjectRepository.findById(id).orElseGet(HomeRecommendSubject::new);
        ValidationUtil.isNull(homeRecommendSubject.getId(),"HomeRecommendSubject","id",id);
        return homeRecommendSubjectMapper.toDto(homeRecommendSubject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HomeRecommendSubjectDTO create(HomeRecommendSubject resources) {
        return homeRecommendSubjectMapper.toDto(homeRecommendSubjectRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HomeRecommendSubject resources) {
        HomeRecommendSubject homeRecommendSubject = homeRecommendSubjectRepository.findById(resources.getId()).orElseGet(HomeRecommendSubject::new);
        ValidationUtil.isNull( homeRecommendSubject.getId(),"HomeRecommendSubject","id",resources.getId());
        homeRecommendSubject.copy(resources);
        homeRecommendSubjectRepository.save(homeRecommendSubject);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            homeRecommendSubjectRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<HomeRecommendSubjectDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (HomeRecommendSubjectDTO homeRecommendSubject : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" subjectId",  homeRecommendSubject.getSubjectId());
            map.put(" subjectName",  homeRecommendSubject.getSubjectName());
            map.put(" recommendStatus",  homeRecommendSubject.getRecommendStatus());
            map.put(" sort",  homeRecommendSubject.getSort());
            map.put("所属店铺", homeRecommendSubject.getStoreId());
            map.put("添加时间", homeRecommendSubject.getAddTime());
            map.put("更新时间", homeRecommendSubject.getModifyTime());
            map.put("逻辑删除", homeRecommendSubject.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}