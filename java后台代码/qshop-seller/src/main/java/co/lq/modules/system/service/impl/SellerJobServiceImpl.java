package co.lq.modules.system.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.system.domain.SellerJob;
import co.lq.modules.system.repository.SellerDeptRepository;
import co.lq.modules.system.repository.SellerJobRepository;
import co.lq.modules.system.service.SellerJobService;
import co.lq.modules.system.service.dto.SellerJobDTO;
import co.lq.modules.system.service.dto.SellerJobQueryCriteria;
import co.lq.modules.system.service.mapper.SellerJobMapper;
import co.lq.utils.FileUtil;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-03-29
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SellerJobServiceImpl implements SellerJobService {

    private final SellerJobRepository  sellerJobRepository;

    private final SellerJobMapper      sellerJobMapper;

    private final SellerDeptRepository deptRepository;

    public SellerJobServiceImpl(SellerJobRepository sellerJobRepository, SellerJobMapper sellerJobMapper,
                                SellerDeptRepository deptRepository) {
        this.sellerJobRepository = sellerJobRepository;
        this.sellerJobMapper = sellerJobMapper;
        this.deptRepository = deptRepository;
    }

    @Override
    public Map<String, Object> queryAll(SellerJobQueryCriteria criteria, Pageable pageable) {
        Page<SellerJob> page = sellerJobRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        List<SellerJobDTO> jobs = new ArrayList<>();
        for (SellerJob sellerJob : page.getContent()) {
            jobs.add(sellerJobMapper.toDto(sellerJob, deptRepository.findNameById(sellerJob.getSellerDept().getPid())));
        }
        return PageUtil.toPage(jobs, page.getTotalElements());
    }

    @Override
    public List<SellerJobDTO> queryAll(SellerJobQueryCriteria criteria) {
        List<SellerJob> list = sellerJobRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return sellerJobMapper.toDto(list);
    }

    @Override
    public SellerJobDTO findById(Long id) {
        SellerJob sellerJob = sellerJobRepository.findById(id).orElseGet(SellerJob::new);
        ValidationUtil.isNull(sellerJob.getId(), "SellerJob", "id", id);
        return sellerJobMapper.toDto(sellerJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SellerJobDTO create(SellerJob resources) {
        return sellerJobMapper.toDto(sellerJobRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SellerJob resources) {
        SellerJob sellerJob = sellerJobRepository.findById(resources.getId()).orElseGet(SellerJob::new);
        ValidationUtil.isNull(sellerJob.getId(), "SellerJob", "id", resources.getId());
        resources.setId(sellerJob.getId());
        sellerJobRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            sellerJobRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SellerJobDTO> sellerJobDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SellerJobDTO sellerJobDTO : sellerJobDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("岗位名称", sellerJobDTO.getName());
            map.put("所属部门", sellerJobDTO.getSellerDept().getName());
            map.put("岗位状态", sellerJobDTO.getEnabled() ? "启用" : "停用");
            map.put("创建日期", sellerJobDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
