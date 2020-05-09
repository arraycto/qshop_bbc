package co.lq.modules.system.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import co.lq.exception.BadRequestException;
import co.lq.modules.system.domain.PlatformDept;
import co.lq.modules.system.repository.PlatformDeptRepository;
import co.lq.modules.system.service.PlatformDeptService;
import co.lq.modules.system.service.dto.PlatformDeptDTO;
import co.lq.modules.system.service.dto.PlatformDeptQueryCriteria;
import co.lq.modules.system.service.mapper.DeptMapper;
import co.lq.utils.FileUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-03-25
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PlatformDeptServiceImpl implements PlatformDeptService {

    private final PlatformDeptRepository deptRepository;

    private final DeptMapper             deptMapper;

    public PlatformDeptServiceImpl(PlatformDeptRepository deptRepository, DeptMapper deptMapper) {
        this.deptRepository = deptRepository;
        this.deptMapper = deptMapper;
    }

    @Override
    public List<PlatformDeptDTO> queryAll(PlatformDeptQueryCriteria criteria) {
        return deptMapper.toDto(deptRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public PlatformDeptDTO findById(Long id) {
        PlatformDept platformDept = deptRepository.findById(id).orElseGet(PlatformDept::new);
        ValidationUtil.isNull(platformDept.getId(), "PlatformDept", "id", id);
        return deptMapper.toDto(platformDept);
    }

    @Override
    public List<PlatformDept> findByPid(long pid) {
        return deptRepository.findByPid(pid);
    }

    @Override
    public Set<PlatformDept> findByRoleIds(Long id) {
        return deptRepository.findByPlatformRoles_Id(id);
    }

    @Override
    public Object buildTree(List<PlatformDeptDTO> platformDeptDtos) {
        Set<PlatformDeptDTO> trees = new LinkedHashSet<>();
        Set<PlatformDeptDTO> depts = new LinkedHashSet<>();
        List<String> deptNames = platformDeptDtos.stream().map(PlatformDeptDTO::getName).collect(Collectors.toList());
        boolean isChild;
        for (PlatformDeptDTO platformDeptDTO : platformDeptDtos) {
            isChild = false;
            if ("0".equals(platformDeptDTO.getPid().toString())) {
                trees.add(platformDeptDTO);
            }
            for (PlatformDeptDTO it : platformDeptDtos) {
                if (it.getPid().equals(platformDeptDTO.getId())) {
                    isChild = true;
                    if (platformDeptDTO.getChildren() == null) {
                        platformDeptDTO.setChildren(new ArrayList<>());
                    }
                    platformDeptDTO.getChildren().add(it);
                }
            }
            if (isChild) {
                depts.add(platformDeptDTO);
            } else if (!deptNames.contains(deptRepository.findNameById(platformDeptDTO.getPid()))) {
                depts.add(platformDeptDTO);
            }
        }

        if (CollectionUtils.isEmpty(trees)) {
            trees = depts;
        }

        Integer totalElements = platformDeptDtos.size();

        Map<String, Object> map = new HashMap<>(2);
        map.put("totalElements", totalElements);
        map.put("content", CollectionUtils.isEmpty(trees) ? platformDeptDtos : trees);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PlatformDeptDTO create(PlatformDept resources) {
        return deptMapper.toDto(deptRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PlatformDept resources) {
        if (resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        PlatformDept platformDept = deptRepository.findById(resources.getId()).orElseGet(PlatformDept::new);
        ValidationUtil.isNull(platformDept.getId(), "PlatformDept", "id", resources.getId());
        resources.setId(platformDept.getId());
        deptRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<PlatformDeptDTO> platformDeptDtos) {
        for (PlatformDeptDTO platformDeptDto : platformDeptDtos) {
            deptRepository.deleteById(platformDeptDto.getId());
        }
    }

    @Override
    public void download(List<PlatformDeptDTO> platformDeptDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PlatformDeptDTO platformDeptDTO : platformDeptDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("部门名称", platformDeptDTO.getName());
            map.put("部门状态", platformDeptDTO.getEnabled() ? "启用" : "停用");
            map.put("创建日期", platformDeptDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public Set<PlatformDeptDTO> getDeleteDepts(List<PlatformDept> menuList, Set<PlatformDeptDTO> platformDeptDtos) {
        for (PlatformDept platformDept : menuList) {
            platformDeptDtos.add(deptMapper.toDto(platformDept));
            List<PlatformDept> platformDepts = deptRepository.findByPid(platformDept.getId());
            if (platformDepts != null && platformDepts.size() != 0) {
                getDeleteDepts(platformDepts, platformDeptDtos);
            }
        }
        return platformDeptDtos;
    }
}
