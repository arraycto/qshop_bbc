package co.lq.modules.activity.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.activity.domain.StorePink;
import co.lq.modules.activity.repository.StorePinkRepository;
import co.lq.modules.activity.service.StoreCombinationService;
import co.lq.modules.activity.service.StorePinkService;
import co.lq.modules.activity.service.dto.StoreCombinationDTO;
import co.lq.modules.activity.service.dto.StorePinkDTO;
import co.lq.modules.activity.service.dto.StorePinkQueryCriteria;
import co.lq.modules.activity.service.mapper.StorePinkMapper;
import co.lq.modules.shop.service.UserService;
import co.lq.modules.shop.service.dto.UserDTO;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-11-18
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StorePinkServiceImpl implements StorePinkService {

    private final StorePinkRepository     storePinkRepository;

    private final StoreCombinationService combinationService;
    private final UserService             userService;

    private final StorePinkMapper         storePinkMapper;

    public StorePinkServiceImpl(StorePinkRepository storePinkRepository, StoreCombinationService combinationService,
                                UserService userService, StorePinkMapper storePinkMapper) {
        this.storePinkRepository = storePinkRepository;
        this.combinationService = combinationService;
        this.userService = userService;
        this.storePinkMapper = storePinkMapper;
    }

    /**
     * 参与拼团的人
     *
     * @param id id
     * @return
     */
    @Override
    public int countPeople(int id) {
        return storePinkRepository.countByKId(id) + 1;
    }

    @Override
    public Map<String, Object> queryAll(StorePinkQueryCriteria criteria, Pageable pageable) {
        criteria.setKId(0);
        Page<StorePink> page = storePinkRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        List<StorePinkDTO> storePinkDTOS = storePinkMapper.toDto(page.getContent());
        for (StorePinkDTO storePinkDTO : storePinkDTOS) {
            StoreCombinationDTO combinationDTO = combinationService.findById(storePinkDTO.getCid());
            UserDTO userDTO = userService.findById(storePinkDTO.getUid());

            storePinkDTO.setAvatar(userDTO.getAvatar());
            storePinkDTO.setNickname(userDTO.getNickname());
            storePinkDTO.setTitle(combinationDTO.getTitle());
            storePinkDTO.setCountPeople(countPeople(storePinkDTO.getId()));
        }
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", storePinkDTOS);
        map.put("totalElements", page.getTotalElements());

        return map;
    }

    @Override
    public List<StorePinkDTO> queryAll(StorePinkQueryCriteria criteria) {
        return storePinkMapper.toDto(storePinkRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StorePinkDTO findById(Long id) {
        Optional<StorePink> storePink = storePinkRepository.findById(id);
        ValidationUtil.isNull(storePink, "StorePink", "id", id);
        return storePinkMapper.toDto(storePink.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StorePinkDTO create(StorePink resources) {
        return storePinkMapper.toDto(storePinkRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StorePink resources) {
        Optional<StorePink> optionalStorePink = storePinkRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalStorePink, "StorePink", "id", resources.getId());
        StorePink storePink = optionalStorePink.get();
        storePink.copy(resources);
        storePinkRepository.save(storePink);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        storePinkRepository.deleteById(id);
    }
}
