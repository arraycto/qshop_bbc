package co.lq.modules.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import co.lq.exception.EntityExistException;
import co.lq.exception.EntityNotFoundException;
import co.lq.modules.system.domain.SellerUser;
import co.lq.modules.system.domain.SellerUserAvatar;
import co.lq.modules.system.repository.SellerUserAvatarRepository;
import co.lq.modules.system.repository.SellerUserRepository;
import co.lq.modules.system.service.SellerService;
import co.lq.modules.system.service.dto.SellerDTO;
import co.lq.modules.system.service.dto.SellerQueryCriteria;
import co.lq.modules.system.service.dto.SellerRoleSmallDTO;
import co.lq.modules.system.service.mapper.SellerMapper;
import co.lq.utils.FileUtil;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.RedisUtils;
import co.lq.utils.SecurityUtils;
import co.lq.utils.StringUtils;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2018-11-23
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SellerServiceImpl implements SellerService {

    private final SellerUserRepository       sellerUserRepository;
    private final SellerMapper               sellerMapper;
    private final RedisUtils                 redisUtils;
    private final SellerUserAvatarRepository sellerUserAvatarRepository;

    @Value("${file.avatar}")
    private String                           avatar;

    public SellerServiceImpl(SellerUserRepository sellerUserRepository, SellerMapper sellerMapper,
                             RedisUtils redisUtils, SellerUserAvatarRepository sellerUserAvatarRepository) {
        this.sellerUserRepository = sellerUserRepository;
        this.sellerMapper = sellerMapper;
        this.redisUtils = redisUtils;
        this.sellerUserAvatarRepository = sellerUserAvatarRepository;
    }

    @Override
    public Object queryAll(SellerQueryCriteria criteria, Pageable pageable) {
        Page<SellerUser> page = sellerUserRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(sellerMapper::toDto));
    }

    @Override
    public List<SellerDTO> queryAll(SellerQueryCriteria criteria) {
        List<SellerUser> sellerUsers = sellerUserRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return sellerMapper.toDto(sellerUsers);
    }

    @Override
    public SellerDTO findById(long id) {
        SellerUser sellerUser = sellerUserRepository.findById(id).orElseGet(SellerUser::new);
        ValidationUtil.isNull(sellerUser.getId(), "SellerUser", "id", id);
        return sellerMapper.toDto(sellerUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SellerDTO create(SellerUser resources) {
        if (sellerUserRepository.findByUsername(resources.getUsername()) != null) {
            throw new EntityExistException(SellerUser.class, "username", resources.getUsername());
        }
        if (sellerUserRepository.findByEmail(resources.getEmail()) != null) {
            throw new EntityExistException(SellerUser.class, "email", resources.getEmail());
        }
        return sellerMapper.toDto(sellerUserRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SellerUser resources) {
        SellerUser sellerUser = sellerUserRepository.findById(resources.getId()).orElseGet(SellerUser::new);
        ValidationUtil.isNull(sellerUser.getId(), "SellerUser", "id", resources.getId());
        SellerUser sellerUser1 = sellerUserRepository.findByUsername(sellerUser.getUsername());
        SellerUser sellerUser2 = sellerUserRepository.findByEmail(sellerUser.getEmail());

        if (sellerUser1 != null && !sellerUser.getId().equals(sellerUser1.getId())) {
            throw new EntityExistException(SellerUser.class, "username", resources.getUsername());
        }

        if (sellerUser2 != null && !sellerUser.getId().equals(sellerUser2.getId())) {
            throw new EntityExistException(SellerUser.class, "email", resources.getEmail());
        }

        // 如果用户的角色改变了，需要手动清理下缓存
        if (!resources.getSellerRoles().equals(sellerUser.getSellerRoles())) {
            String key = "role::loadPermissionByUser:" + sellerUser.getUsername();
            redisUtils.del(key);
            key = "role::findByUsers_Id:" + sellerUser.getId();
            redisUtils.del(key);
        }

        sellerUser.setUsername(resources.getUsername());
        sellerUser.setEmail(resources.getEmail());
        sellerUser.setEnabled(resources.getEnabled());
        sellerUser.setSellerRoles(resources.getSellerRoles());
        sellerUser.setSellerDept(resources.getSellerDept());
        sellerUser.setSellerJob(resources.getSellerJob());
        sellerUser.setPhone(resources.getPhone());
        sellerUser.setNickName(resources.getNickName());
        sellerUser.setSex(resources.getSex());
        sellerUserRepository.save(sellerUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCenter(SellerUser resources) {
        SellerUser sellerUser = sellerUserRepository.findById(resources.getId()).orElseGet(SellerUser::new);
        sellerUser.setNickName(resources.getNickName());
        sellerUser.setPhone(resources.getPhone());
        sellerUser.setSex(resources.getSex());
        sellerUserRepository.save(sellerUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            sellerUserRepository.deleteById(id);
        }
    }

    @Override
    public SellerDTO findByName(String userName) {
        SellerUser sellerUser;
        if (ValidationUtil.isEmail(userName)) {
            sellerUser = sellerUserRepository.findByEmail(userName);
        } else {
            sellerUser = sellerUserRepository.findByUsername(userName);
        }
        if (sellerUser == null) {
            throw new EntityNotFoundException(SellerUser.class, "name", userName);
        } else {
            return sellerMapper.toDto(sellerUser);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String pass) {
        sellerUserRepository.updatePass(username, pass, new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(MultipartFile multipartFile) {
        SellerUser sellerUser = sellerUserRepository.findByUsername(SecurityUtils.getUsername());
        SellerUserAvatar sellerUserAvatar = sellerUser.getSellerUserAvatar();
        String oldPath = "";
        if (sellerUserAvatar != null) {
            oldPath = sellerUserAvatar.getPath();
        }
        File file = FileUtil.upload(multipartFile, avatar);
        assert file != null;
        sellerUserAvatar = sellerUserAvatarRepository.save(new SellerUserAvatar(sellerUserAvatar, file.getName(),
                file.getPath(), FileUtil.getSize(multipartFile.getSize())));
        sellerUser.setSellerUserAvatar(sellerUserAvatar);
        sellerUserRepository.save(sellerUser);
        if (StringUtils.isNotBlank(oldPath)) {
            FileUtil.del(oldPath);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String email) {
        sellerUserRepository.updateEmail(username, email);
    }

    @Override
    public void download(List<SellerDTO> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SellerDTO sellerDTO : queryAll) {
            List<String> roles = sellerDTO.getSellerRoles().stream().map(SellerRoleSmallDTO::getName).collect(
                    Collectors.toList());
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户名", sellerDTO.getUsername());
            map.put("头像", sellerDTO.getAvatar());
            map.put("邮箱", sellerDTO.getEmail());
            map.put("状态", sellerDTO.getEnabled() ? "启用" : "禁用");
            map.put("手机号码", sellerDTO.getPhone());
            map.put("角色", roles);
            map.put("部门", sellerDTO.getSellerDept().getName());
            map.put("岗位", sellerDTO.getSellerJob().getName());
            map.put("最后修改密码的时间", sellerDTO.getLastPasswordResetTime());
            map.put("创建日期", sellerDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
