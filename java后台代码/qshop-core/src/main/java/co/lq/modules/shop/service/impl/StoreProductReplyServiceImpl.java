package co.lq.modules.shop.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.shop.domain.StoreProductReply;
import co.lq.modules.shop.repository.StoreProductReplyRepository;
import co.lq.modules.shop.service.StoreProductReplyService;
import co.lq.modules.shop.service.StoreProductService;
import co.lq.modules.shop.service.UserService;
import co.lq.modules.shop.service.dto.StoreProductReplyDTO;
import co.lq.modules.shop.service.dto.StoreProductReplyQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreProductReplyMapper;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-11-03
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreProductReplyServiceImpl implements StoreProductReplyService {

    private final StoreProductReplyRepository storeProductReplyRepository;

    private final StoreProductReplyMapper     storeProductReplyMapper;

    private final UserService                 userService;
    private final StoreProductService         productService;

    public StoreProductReplyServiceImpl(StoreProductReplyRepository storeProductReplyRepository,
                                        StoreProductReplyMapper storeProductReplyMapper, UserService userService,
                                        StoreProductService productService) {
        this.storeProductReplyRepository = storeProductReplyRepository;
        this.storeProductReplyMapper = storeProductReplyMapper;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public Map<String, Object> queryAll(StoreProductReplyQueryCriteria criteria, Pageable pageable) {
        criteria.setIsDel(0);
        Page<StoreProductReply> page = storeProductReplyRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        List<StoreProductReplyDTO> productReplyDTOS = new ArrayList<>();
        for (StoreProductReply reply : page.getContent()) {

            StoreProductReplyDTO productReplyDTO = storeProductReplyMapper.toDto(reply);
            try {
                productReplyDTO.setUsername(userService.findById(reply.getUid()).getAccount());
                productReplyDTO.setProductName(productService.findById(reply.getProductId()).getProductName());
            } catch (Exception e) {
                continue;
            }
            productReplyDTOS.add(productReplyDTO);
        }
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", productReplyDTOS);
        map.put("totalElements", page.getTotalElements());
        return map;
    }

    @Override
    public List<StoreProductReplyDTO> queryAll(StoreProductReplyQueryCriteria criteria) {
        criteria.setIsDel(0);
        return storeProductReplyMapper.toDto(storeProductReplyRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreProductReplyDTO findById(Long id) {
        Optional<StoreProductReply> storeProductReply = storeProductReplyRepository.findById(id);
        ValidationUtil.isNull(storeProductReply, "StoreProductReply", "id", id);
        return storeProductReplyMapper.toDto(storeProductReply.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreProductReplyDTO create(StoreProductReply resources) {
        return storeProductReplyMapper.toDto(storeProductReplyRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreProductReply resources) {
        Optional<StoreProductReply> optionalStoreProductReply = storeProductReplyRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalStoreProductReply, "StoreProductReply", "id", resources.getId());
        StoreProductReply storeProductReply = optionalStoreProductReply.get();
        storeProductReply.copy(resources);
        storeProductReplyRepository.save(storeProductReply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Optional<StoreProductReply> optionalStoreProductReply = storeProductReplyRepository.findById(id);
        ValidationUtil.isNull(optionalStoreProductReply, "StoreProductReply", "id", id);
        StoreProductReply storeProductReply = optionalStoreProductReply.get();
        storeProductReply.setIsDel(0);
        storeProductReplyRepository.save(storeProductReply);
    }
}
