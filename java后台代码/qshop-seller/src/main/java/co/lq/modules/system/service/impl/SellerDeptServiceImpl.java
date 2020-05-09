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
import co.lq.modules.system.domain.SellerDept;
import co.lq.modules.system.repository.SellerDeptRepository;
import co.lq.modules.system.service.SellerDeptService;
import co.lq.modules.system.service.dto.SellerDeptDTO;
import co.lq.modules.system.service.dto.SellerDeptQueryCriteria;
import co.lq.modules.system.service.mapper.SellerDeptMapper;
import co.lq.utils.FileUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-03-25
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SellerDeptServiceImpl implements SellerDeptService {

    private final SellerDeptRepository deptRepository;

    private final SellerDeptMapper     sellerDeptMapper;

    public SellerDeptServiceImpl(SellerDeptRepository deptRepository, SellerDeptMapper sellerDeptMapper) {
        this.deptRepository = deptRepository;
        this.sellerDeptMapper = sellerDeptMapper;
    }

    @Override
    public List<SellerDeptDTO> queryAll(SellerDeptQueryCriteria criteria) {
        return sellerDeptMapper.toDto(deptRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public SellerDeptDTO findById(Long id) {
        SellerDept dept = deptRepository.findById(id).orElseGet(SellerDept::new);
        ValidationUtil.isNull(dept.getId(), "SellerDept", "id", id);
        return sellerDeptMapper.toDto(dept);
    }

    @Override
    public List<SellerDept> findByPid(long pid) {
        return deptRepository.findByPid(pid);
    }

    @Override
    public List<SellerDept> findByPidAndStoreId(long pid, Long storeId) {
        return deptRepository.findByPidAndStoreId(pid, storeId);
    }

    @Override
    public Set<SellerDept> findByRoleIds(Long id) {
        return deptRepository.findBySellerRoles_Id(id);
    }

    @Override
    public Object buildTree(List<SellerDeptDTO> sellerDeptDtos) {
        Set<SellerDeptDTO> trees = new LinkedHashSet<>();
        Set<SellerDeptDTO> depts = new LinkedHashSet<>();
        List<String> deptNames = sellerDeptDtos.stream().map(SellerDeptDTO::getName).collect(Collectors.toList());
        boolean isChild;
        for (SellerDeptDTO sellerDeptDTO : sellerDeptDtos) {
            isChild = false;
            if ("0".equals(sellerDeptDTO.getPid().toString())) {
                trees.add(sellerDeptDTO);
            }
            for (SellerDeptDTO it : sellerDeptDtos) {
                if (it.getPid().equals(sellerDeptDTO.getId())) {
                    isChild = true;
                    if (sellerDeptDTO.getChildren() == null) {
                        sellerDeptDTO.setChildren(new ArrayList<>());
                    }
                    sellerDeptDTO.getChildren().add(it);
                }
            }
            if (isChild) {
                depts.add(sellerDeptDTO);
            } else if (!deptNames.contains(deptRepository.findNameById(sellerDeptDTO.getPid()))) {
                depts.add(sellerDeptDTO);
            }
        }

        if (CollectionUtils.isEmpty(trees)) {
            trees = depts;
        }

        Integer totalElements = sellerDeptDtos.size();

        Map<String, Object> map = new HashMap<>(2);
        map.put("totalElements", totalElements);
        map.put("content", CollectionUtils.isEmpty(trees) ? sellerDeptDtos : trees);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SellerDeptDTO create(SellerDept resources) {
        return sellerDeptMapper.toDto(deptRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SellerDept resources) {
        if (resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        SellerDept dept = deptRepository.findById(resources.getId()).orElseGet(SellerDept::new);
        ValidationUtil.isNull(dept.getId(), "SellerDept", "id", resources.getId());
        resources.setId(dept.getId());
        deptRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<SellerDeptDTO> sellerDeptDtos) {
        for (SellerDeptDTO sellerDeptDto : sellerDeptDtos) {
            deptRepository.deleteById(sellerDeptDto.getId());
        }
    }

    @Override
    public void download(List<SellerDeptDTO> sellerDeptDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SellerDeptDTO sellerDeptDTO : sellerDeptDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("部门名称", sellerDeptDTO.getName());
            map.put("部门状态", sellerDeptDTO.getEnabled() ? "启用" : "停用");
            map.put("创建日期", sellerDeptDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public Set<SellerDeptDTO> getDeleteDepts(List<SellerDept> menuList, Set<SellerDeptDTO> sellerDeptDtos) {
        for (SellerDept dept : menuList) {
            sellerDeptDtos.add(sellerDeptMapper.toDto(dept));
            List<SellerDept> depts = deptRepository.findByPid(dept.getId());
            if (depts != null && depts.size() != 0) {
                getDeleteDepts(depts, sellerDeptDtos);
            }
        }
        return sellerDeptDtos;
    }
}
