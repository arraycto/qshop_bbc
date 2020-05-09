package co.lq.modules.shop.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.shop.domain.UserTask;
import co.lq.modules.shop.repository.UserTaskRepository;
import co.lq.modules.shop.service.UserLevelService;
import co.lq.modules.shop.service.UserTaskService;
import co.lq.modules.shop.service.dto.UserTaskDTO;
import co.lq.modules.shop.service.dto.UserTaskQueryCriteria;
import co.lq.modules.shop.service.mapper.UserTaskMapper;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-12-04
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserTaskServiceImpl implements UserTaskService {

    private final UserTaskRepository userTaskRepository;

    private final UserTaskMapper     userTaskMapper;

    private final UserLevelService   userLevelService;

    public UserTaskServiceImpl(UserTaskRepository userTaskRepository, UserTaskMapper userTaskMapper,
                               UserLevelService userLevelService) {
        this.userTaskRepository = userTaskRepository;
        this.userTaskMapper = userTaskMapper;
        this.userLevelService = userLevelService;
    }

    @Override
    public Map<String, Object> queryAll(UserTaskQueryCriteria criteria, Pageable pageable) {

        Page<UserTask> page = userTaskRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);

        List<UserTaskDTO> userTaskDTOS = userTaskMapper.toDto(page.getContent());
        for (UserTaskDTO userTaskDTO : userTaskDTOS) {
            userTaskDTO.setLevalName(userLevelService.findById(userTaskDTO.getLevelId()).getName());
        }

        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", userTaskDTOS);
        map.put("totalElements", page.getTotalElements());

        return map;

    }

    @Override
    public List<UserTaskDTO> queryAll(UserTaskQueryCriteria criteria) {
        return userTaskMapper.toDto(userTaskRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public UserTaskDTO findById(Long id) {
        Optional<UserTask> systemUserTask = userTaskRepository.findById(id);
        ValidationUtil.isNull(systemUserTask, "UserTask", "id", id);
        return userTaskMapper.toDto(systemUserTask.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserTaskDTO create(UserTask resources) {
        return userTaskMapper.toDto(userTaskRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserTask resources) {
        Optional<UserTask> optionalSystemUserTask = userTaskRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalSystemUserTask, "UserTask", "id", resources.getId());
        UserTask userTask = optionalSystemUserTask.get();
        userTask.copy(resources);
        userTaskRepository.save(userTask);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        userTaskRepository.deleteById(id);
    }

    /**
     * 任务类型
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getTaskType() {
        List<Map<String, Object>> list = null;
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "SatisfactionIntegral");
        map.put("type", "SatisfactionIntegral");
        map.put("type", "SatisfactionIntegral");
        map.put("type", "SatisfactionIntegral");
        map.put("type", "SatisfactionIntegral");
        map.put("type", "SatisfactionIntegral");
        return null;
    }
}
