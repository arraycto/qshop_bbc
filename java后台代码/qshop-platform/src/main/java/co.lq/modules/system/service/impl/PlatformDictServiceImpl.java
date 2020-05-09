package co.lq.modules.system.service.impl;

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

import cn.hutool.core.collection.CollectionUtil;
import co.lq.modules.system.domain.Dict;
import co.lq.modules.system.repository.PlatformDictRepository;
import co.lq.modules.system.service.PlatformDictService;
import co.lq.modules.system.service.dto.DictDTO;
import co.lq.modules.system.service.dto.DictDetailDTO;
import co.lq.modules.system.service.dto.DictQueryCriteria;
import co.lq.modules.system.service.mapper.DictMapper;
import co.lq.utils.FileUtil;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-04-10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PlatformDictServiceImpl implements PlatformDictService {

    private final PlatformDictRepository platformDictRepository;

    private final DictMapper             dictMapper;

    public PlatformDictServiceImpl(PlatformDictRepository platformDictRepository, DictMapper dictMapper) {
        this.platformDictRepository = platformDictRepository;
        this.dictMapper = dictMapper;
    }

    @Override
    public Map<String, Object> queryAll(DictQueryCriteria dict, Pageable pageable) {
        Page<Dict> page = platformDictRepository.findAll((root, query, cb) -> QueryHelp.getPredicate(root, dict, cb),
                pageable);
        return PageUtil.toPage(page.map(dictMapper::toDto));
    }

    @Override
    public List<DictDTO> queryAll(DictQueryCriteria dict) {
        List<Dict> list = platformDictRepository.findAll((root, query, cb) -> QueryHelp.getPredicate(root, dict, cb));
        return dictMapper.toDto(list);
    }

    @Override
    public DictDTO findById(Long id) {
        Dict dict = platformDictRepository.findById(id).orElseGet(Dict::new);
        ValidationUtil.isNull(dict.getId(), "Dict", "id", id);
        return dictMapper.toDto(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictDTO create(Dict resources) {
        return dictMapper.toDto(platformDictRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Dict resources) {
        Dict dict = platformDictRepository.findById(resources.getId()).orElseGet(Dict::new);
        ValidationUtil.isNull(dict.getId(), "Dict", "id", resources.getId());
        resources.setId(dict.getId());
        platformDictRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        platformDictRepository.deleteById(id);
    }

    @Override
    public void download(List<DictDTO> dictDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DictDTO dictDTO : dictDtos) {
            if (CollectionUtil.isNotEmpty(dictDTO.getDictDetails())) {
                for (DictDetailDTO dictDetail : dictDTO.getDictDetails()) {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("字典名称", dictDTO.getName());
                    map.put("字典描述", dictDTO.getRemark());
                    map.put("字典标签", dictDetail.getLabel());
                    map.put("字典值", dictDetail.getValue());
                    map.put("创建日期", dictDetail.getCreateTime());
                    list.add(map);
                }
            } else {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("字典名称", dictDTO.getName());
                map.put("字典描述", dictDTO.getRemark());
                map.put("字典标签", null);
                map.put("字典值", null);
                map.put("创建日期", dictDTO.getCreateTime());
                list.add(map);
            }
        }
        FileUtil.downloadExcel(list, response);
    }
}
