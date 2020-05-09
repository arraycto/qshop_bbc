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

import co.lq.modules.system.domain.PlatformJob;
import co.lq.modules.system.repository.PlatformDeptRepository;
import co.lq.modules.system.repository.PlatformJobRepository;
import co.lq.modules.system.service.PlatformJobService;
import co.lq.modules.system.service.dto.PlatformJobDTO;
import co.lq.modules.system.service.dto.PlatformJobQueryCriteria;
import co.lq.modules.system.service.mapper.JobMapper;
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
public class PlatformJobServiceImpl implements PlatformJobService {

    private final PlatformJobRepository  platformJobRepository;

    private final JobMapper              jobMapper;

    private final PlatformDeptRepository deptRepository;

    public PlatformJobServiceImpl(PlatformJobRepository platformJobRepository, JobMapper jobMapper,
                                  PlatformDeptRepository deptRepository) {
        this.platformJobRepository = platformJobRepository;
        this.jobMapper = jobMapper;
        this.deptRepository = deptRepository;
    }

    @Override
    public Map<String, Object> queryAll(PlatformJobQueryCriteria criteria, Pageable pageable) {
        Page<PlatformJob> page = platformJobRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        List<PlatformJobDTO> jobs = new ArrayList<>();
        for (PlatformJob platformJob : page.getContent()) {
            jobs.add(jobMapper.toDto(platformJob, deptRepository.findNameById(platformJob.getPlatformDept().getPid())));
        }
        return PageUtil.toPage(jobs, page.getTotalElements());
    }

    @Override
    public List<PlatformJobDTO> queryAll(PlatformJobQueryCriteria criteria) {
        List<PlatformJob> list = platformJobRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return jobMapper.toDto(list);
    }

    @Override
    public PlatformJobDTO findById(Long id) {
        PlatformJob platformJob = platformJobRepository.findById(id).orElseGet(PlatformJob::new);
        ValidationUtil.isNull(platformJob.getId(), "PlatformJob", "id", id);
        return jobMapper.toDto(platformJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PlatformJobDTO create(PlatformJob resources) {
        return jobMapper.toDto(platformJobRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PlatformJob resources) {
        PlatformJob platformJob = platformJobRepository.findById(resources.getId()).orElseGet(PlatformJob::new);
        ValidationUtil.isNull(platformJob.getId(), "PlatformJob", "id", resources.getId());
        resources.setId(platformJob.getId());
        platformJobRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            platformJobRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<PlatformJobDTO> platformJobDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PlatformJobDTO platformJobDTO : platformJobDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("岗位名称", platformJobDTO.getName());
            map.put("所属部门", platformJobDTO.getPlatformDept().getName());
            map.put("岗位状态", platformJobDTO.getEnabled() ? "启用" : "停用");
            map.put("创建日期", platformJobDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
