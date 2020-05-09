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

import co.lq.modules.shop.domain.CmsPrefrenceArea;
import co.lq.modules.shop.repository.CmsPrefrenceAreaRepository;
import co.lq.modules.shop.service.CmsPrefrenceAreaService;
import co.lq.modules.shop.service.dto.CmsPrefrenceAreaDTO;
import co.lq.modules.shop.service.dto.CmsPrefrenceAreaQueryCriteria;
import co.lq.modules.shop.service.mapper.CmsPrefrenceAreaMapper;
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
 * @date 2020-04-11
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CmsPrefrenceAreaServiceImpl implements CmsPrefrenceAreaService {

    private final CmsPrefrenceAreaRepository cmsPrefrenceAreaRepository;

    private final CmsPrefrenceAreaMapper     cmsPrefrenceAreaMapper;

    public CmsPrefrenceAreaServiceImpl(CmsPrefrenceAreaRepository cmsPrefrenceAreaRepository,
                                       CmsPrefrenceAreaMapper cmsPrefrenceAreaMapper) {
        this.cmsPrefrenceAreaRepository = cmsPrefrenceAreaRepository;
        this.cmsPrefrenceAreaMapper = cmsPrefrenceAreaMapper;
    }

    @Override
    public Map<String, Object> queryAll(CmsPrefrenceAreaQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<CmsPrefrenceArea> page = cmsPrefrenceAreaRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(cmsPrefrenceAreaMapper::toDto));
    }

    @Override
    public List<CmsPrefrenceAreaDTO> queryAll(CmsPrefrenceAreaQueryCriteria criteria) {
        criteria.setDeleted(0);
        return cmsPrefrenceAreaMapper.toDto(cmsPrefrenceAreaRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public CmsPrefrenceAreaDTO findById(Long id) {
        CmsPrefrenceArea cmsPrefrenceArea = cmsPrefrenceAreaRepository.findById(id).orElseGet(CmsPrefrenceArea::new);
        ValidationUtil.isNull(cmsPrefrenceArea.getId(), "CmsPrefrenceArea", "id", id);
        return cmsPrefrenceAreaMapper.toDto(cmsPrefrenceArea);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmsPrefrenceAreaDTO create(CmsPrefrenceArea resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return cmsPrefrenceAreaMapper.toDto(cmsPrefrenceAreaRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CmsPrefrenceArea resources) {
        CmsPrefrenceArea cmsPrefrenceArea = cmsPrefrenceAreaRepository.findById(resources.getId())
                .orElseGet(CmsPrefrenceArea::new);
        ValidationUtil.isNull(cmsPrefrenceArea.getId(), "CmsPrefrenceArea", "id", resources.getId());
        cmsPrefrenceArea.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        cmsPrefrenceArea.setModifyTime(timestamp);
        cmsPrefrenceAreaRepository.save(cmsPrefrenceArea);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            CmsPrefrenceArea cmsPrefrenceArea = cmsPrefrenceAreaRepository.findById(id)
                    .orElseGet(CmsPrefrenceArea::new);
            ValidationUtil.isNull(cmsPrefrenceArea.getId(), "CmsPrefrenceArea", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            cmsPrefrenceArea.setModifyTime(timestamp);
            cmsPrefrenceArea.setDeleted(1);
            cmsPrefrenceAreaRepository.save(cmsPrefrenceArea);
        }
    }

    @Override
    public void download(List<CmsPrefrenceAreaDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CmsPrefrenceAreaDTO cmsPrefrenceArea : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("标题", cmsPrefrenceArea.getName());
            map.put("子标题", cmsPrefrenceArea.getSubTitle());
            map.put("展示图片", cmsPrefrenceArea.getPic());
            map.put("排序", cmsPrefrenceArea.getSort());
            map.put("状态", cmsPrefrenceArea.getShowStatus());
            map.put("添加时间", cmsPrefrenceArea.getAddTime());
            map.put("更新时间", cmsPrefrenceArea.getModifyTime());
            map.put("逻辑删除", cmsPrefrenceArea.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
