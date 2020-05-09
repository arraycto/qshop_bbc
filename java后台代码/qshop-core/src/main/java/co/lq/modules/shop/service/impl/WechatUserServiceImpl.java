package co.lq.modules.shop.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.shop.domain.WechatUser;
import co.lq.modules.shop.repository.WechatUserRepository;
import co.lq.modules.shop.service.WechatUserService;
import co.lq.modules.shop.service.dto.WechatUserDTO;
import co.lq.modules.shop.service.dto.WechatUserQueryCriteria;
import co.lq.modules.shop.service.mapper.WechatUserMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-12-13
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WechatUserServiceImpl implements WechatUserService {

    private final WechatUserRepository wechatUserRepository;

    private final WechatUserMapper     wechatUserMapper;

    public WechatUserServiceImpl(WechatUserRepository wechatUserRepository, WechatUserMapper wechatUserMapper) {
        this.wechatUserRepository = wechatUserRepository;
        this.wechatUserMapper = wechatUserMapper;
    }

    @Override
    public Map<String, Object> queryAll(WechatUserQueryCriteria criteria, Pageable pageable) {
        Page<WechatUser> page = wechatUserRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(wechatUserMapper::toDto));
    }

    @Override
    public List<WechatUserDTO> queryAll(WechatUserQueryCriteria criteria) {
        return wechatUserMapper.toDto(wechatUserRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public WechatUserDTO findById(Long uid) {
        Optional<WechatUser> wechatUser = wechatUserRepository.findById(uid);
        ValidationUtil.isNull(wechatUser, "WechatUser", "uid", uid);
        return wechatUserMapper.toDto(wechatUser.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WechatUserDTO create(WechatUser resources) {
        return wechatUserMapper.toDto(wechatUserRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(WechatUser resources) {
        Optional<WechatUser> optionalWechatUser = wechatUserRepository.findById(resources.getUid());
        ValidationUtil.isNull(optionalWechatUser, "WechatUser", "id", resources.getUid());
        WechatUser wechatUser = optionalWechatUser.get();
        wechatUser.copy(resources);
        wechatUserRepository.save(wechatUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long uid) {
        wechatUserRepository.deleteById(uid);
    }
}
