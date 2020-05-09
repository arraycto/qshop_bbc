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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import co.lq.exception.EntityExistException;
import co.lq.exception.EntityNotFoundException;
import co.lq.modules.system.domain.PlatformUser;
import co.lq.modules.system.domain.PlatformUserAvatar;
import co.lq.modules.system.repository.PlatformUserAvatarRepository;
import co.lq.modules.system.repository.PlatformUserRepository;
import co.lq.modules.system.service.PlatformUserService;
import co.lq.modules.system.service.dto.PlatformRoleDTO;
import co.lq.modules.system.service.dto.PlatformUserDTO;
import co.lq.modules.system.service.dto.PlatformUserQueryCriteria;
import co.lq.modules.system.service.mapper.PlatformUserMapper;
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
public class PlatformUserServiceImpl implements PlatformUserService {

    private final PlatformUserRepository       platformUserRepository;
    private final PlatformUserMapper           platformUserMapper;
    private final RedisUtils                   redisUtils;
    private final PlatformUserAvatarRepository platformUserAvatarRepository;

    @Value("${file.avatar}")
    private String                             avatar;

    public PlatformUserServiceImpl(PlatformUserRepository platformUserRepository, PlatformUserMapper platformUserMapper,
                                   RedisUtils redisUtils, PlatformUserAvatarRepository platformUserAvatarRepository) {
        this.platformUserRepository = platformUserRepository;
        this.platformUserMapper = platformUserMapper;
        this.redisUtils = redisUtils;
        this.platformUserAvatarRepository = platformUserAvatarRepository;
    }

    @Override
    public Object queryAll(PlatformUserQueryCriteria criteria, Pageable pageable) {
        Page<PlatformUser> page = platformUserRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(platformUserMapper::toDto));
    }

    @Override
    public List<PlatformUserDTO> queryAll(PlatformUserQueryCriteria criteria) {
        List<PlatformUser> platformUsers = platformUserRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return platformUserMapper.toDto(platformUsers);
    }

    @Override
    public PlatformUserDTO findById(long id) {
        PlatformUser platformUser = platformUserRepository.findById(id).orElseGet(PlatformUser::new);
        ValidationUtil.isNull(platformUser.getId(), "PlatformUser", "id", id);
        return platformUserMapper.toDto(platformUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PlatformUserDTO create(PlatformUser resources) {
        if (platformUserRepository.findByUsername(resources.getUsername()) != null) {
            throw new EntityExistException(PlatformUser.class, "username", resources.getUsername());
        }
        if (platformUserRepository.findByEmail(resources.getEmail()) != null) {
            throw new EntityExistException(PlatformUser.class, "email", resources.getEmail());
        }
        return platformUserMapper.toDto(platformUserRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PlatformUser resources) {
        PlatformUser platformUser = platformUserRepository.findById(resources.getId()).orElseGet(PlatformUser::new);
        ValidationUtil.isNull(platformUser.getId(), "PlatformUser", "id", resources.getId());
        PlatformUser platformUser1 = platformUserRepository.findByUsername(platformUser.getUsername());
        PlatformUser platformUser2 = platformUserRepository.findByEmail(platformUser.getEmail());

        if (platformUser1 != null && !platformUser.getId().equals(platformUser1.getId())) {
            throw new EntityExistException(PlatformUser.class, "username", resources.getUsername());
        }

        if (platformUser2 != null && !platformUser.getId().equals(platformUser2.getId())) {
            throw new EntityExistException(PlatformUser.class, "email", resources.getEmail());
        }

        // 如果用户的角色改变了，需要手动清理下缓存
        if (!resources.getPlatformRoles().equals(platformUser.getPlatformRoles())) {
            String key = "role::loadPermissionByUser:" + platformUser.getUsername();
            redisUtils.del(key);
            key = "role::findByUsers_Id:" + platformUser.getId();
            redisUtils.del(key);
        }

        platformUser.setUsername(resources.getUsername());
        platformUser.setEmail(resources.getEmail());
        platformUser.setEnabled(resources.getEnabled());
        platformUser.setPlatformRoles(resources.getPlatformRoles());
        platformUser.setPlatformDept(resources.getPlatformDept());
        platformUser.setPlatformJob(resources.getPlatformJob());
        platformUser.setPhone(resources.getPhone());
        platformUser.setNickName(resources.getNickName());
        platformUser.setSex(resources.getSex());
        platformUserRepository.save(platformUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCenter(PlatformUser resources) {
        PlatformUser platformUser = platformUserRepository.findById(resources.getId()).orElseGet(PlatformUser::new);
        platformUser.setNickName(resources.getNickName());
        platformUser.setPhone(resources.getPhone());
        platformUser.setSex(resources.getSex());
        platformUserRepository.save(platformUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            platformUserRepository.deleteById(id);
        }
    }

    @Override
    public PlatformUserDTO findByName(String userName) {
        PlatformUser platformUser;
        if (ValidationUtil.isEmail(userName)) {
            platformUser = platformUserRepository.findByEmail(userName);
        } else {
            platformUser = platformUserRepository.findByUsername(userName);
        }
        if (platformUser == null) {
            throw new EntityNotFoundException(PlatformUser.class, "name", userName);
        } else {
            return platformUserMapper.toDto(platformUser);
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String pass) {
        platformUserRepository.updatePass(username, pass, new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(MultipartFile multipartFile) {
        PlatformUser platformUser = platformUserRepository.findByUsername(SecurityUtils.getUsername());
        PlatformUserAvatar platformUserAvatar = platformUser.getPlatformUserAvatar();
        String oldPath = "";
        if (platformUserAvatar != null) {
            oldPath = platformUserAvatar.getPath();
        }
        File file = FileUtil.upload(multipartFile, avatar);
        assert file != null;
        platformUserAvatar = platformUserAvatarRepository.save(new PlatformUserAvatar(platformUserAvatar,
                file.getName(), file.getPath(), FileUtil.getSize(multipartFile.getSize())));
        platformUser.setPlatformUserAvatar(platformUserAvatar);
        platformUserRepository.save(platformUser);
        if (StringUtils.isNotBlank(oldPath)) {
            FileUtil.del(oldPath);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String email) {
        platformUserRepository.updateEmail(username, email);
    }

    @Override
    public void download(List<PlatformUserDTO> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PlatformUserDTO platformUserDTO : queryAll) {
            List<String> roles = platformUserDTO.getPlatformRoles().stream().map(PlatformRoleDTO::getName).collect(
                    Collectors.toList());
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户名", platformUserDTO.getUsername());
            map.put("头像", platformUserDTO.getAvatar());
            map.put("邮箱", platformUserDTO.getEmail());
            map.put("状态", platformUserDTO.getEnabled() ? "启用" : "禁用");
            map.put("手机号码", platformUserDTO.getPhone());
            map.put("角色", roles);
            map.put("部门", platformUserDTO.getPlatformDept().getName());
            map.put("岗位", platformUserDTO.getPlatformJob().getName());
            map.put("最后修改密码的时间", platformUserDTO.getLastPasswordResetTime());
            map.put("创建日期", platformUserDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
