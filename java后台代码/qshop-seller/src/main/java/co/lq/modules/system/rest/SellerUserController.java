package co.lq.modules.system.rest;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import co.lq.aop.log.Log;
import co.lq.config.DataScope;
import co.lq.domain.VerificationCode;
import co.lq.exception.BadRequestException;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.system.domain.SellerUser;
import co.lq.modules.system.domain.vo.StoreUserPassVo;
import co.lq.modules.system.service.SellerDeptService;
import co.lq.modules.system.service.SellerRoleService;
import co.lq.modules.system.service.SellerService;
import co.lq.modules.system.service.dto.SellerDTO;
import co.lq.modules.system.service.dto.SellerQueryCriteria;
import co.lq.modules.system.service.dto.SellerRoleSmallDTO;
import co.lq.service.VerificationCodeService;
import co.lq.utils.PageUtil;
import co.lq.utils.QshopConstant;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2018-11-23
 */
@Api(tags = "系统：用户管理")
@RestController
@RequestMapping("/api/users")
public class SellerUserController {

    @Value("${rsa.private_key}")
    private String                        privateKey;
    private final PasswordEncoder         passwordEncoder;
    private final SellerService           sellerService;
    private final DataScope               dataScope;
    private final SellerDeptService       sellerDeptService;
    private final SellerRoleService       sellerRoleService;
    private final UserDetailsService      userDetailsService;
    private final VerificationCodeService verificationCodeService;

    public SellerUserController(PasswordEncoder passwordEncoder, SellerService sellerService, DataScope dataScope,
                                SellerDeptService sellerDeptService, SellerRoleService sellerRoleService,
                                UserDetailsService userDetailsService,
                                VerificationCodeService verificationCodeService) {
        this.passwordEncoder = passwordEncoder;
        this.sellerService = sellerService;
        this.dataScope = dataScope;
        this.sellerDeptService = sellerDeptService;
        this.sellerRoleService = sellerRoleService;
        this.userDetailsService = userDetailsService;
        this.verificationCodeService = verificationCodeService;
    }

    @Log("导出用户数据")
    @ApiOperation("导出用户数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','user:list')")
    public void download(HttpServletResponse response, SellerQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        sellerService.download(sellerService.queryAll(criteria), response);
    }

    @Log("查询用户")
    @ApiOperation("查询用户")
    @GetMapping
    @PreAuthorize("@el.check('admin','user:list')")
    public ResponseEntity<Object> getUsers(SellerQueryCriteria criteria, Pageable pageable) {
        Set<Long> deptSet = new HashSet<>();
        Set<Long> result = new HashSet<>();
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        if (!ObjectUtils.isEmpty(criteria.getDeptId())) {
            deptSet.add(criteria.getDeptId());
            deptSet.addAll(dataScope.getDeptChildren(
                    sellerDeptService.findByPidAndStoreId(criteria.getDeptId(), jwtUser.getStoreId())));
        }
        // 数据权限
        Set<Long> deptIds = dataScope.getDeptIds();
        // 查询条件不为空并且数据权限不为空则取交集
        if (!CollectionUtils.isEmpty(deptIds) && !CollectionUtils.isEmpty(deptSet)) {
            // 取交集
            result.addAll(deptSet);
            result.retainAll(deptIds);
            // 若无交集，则代表无数据权限
            criteria.setDeptIds(result);
            if (result.size() == 0) {
                return new ResponseEntity<>(PageUtil.toPage(null, 0), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(sellerService.queryAll(criteria, pageable), HttpStatus.OK);
            }
            // 否则取并集
        } else {
            result.addAll(deptSet);
            result.addAll(deptIds);
            criteria.setDeptIds(result);
            return new ResponseEntity<>(sellerService.queryAll(criteria, pageable), HttpStatus.OK);
        }
    }

    @Log("新增用户")
    @ApiOperation("新增用户")
    @PostMapping
    @PreAuthorize("@el.check('admin','user:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody SellerUser resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        checkLevel(resources);
        // 默认密码 123456
        resources.setPassword(passwordEncoder.encode("123456"));
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(sellerService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改用户")
    @ApiOperation("修改用户")
    @PutMapping
    @PreAuthorize("@el.check('admin','user:edit')")
    public ResponseEntity<Object> update(@Validated(SellerUser.Update.class) @RequestBody SellerUser resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        checkLevel(resources);
        sellerService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("修改用户：个人中心")
    @ApiOperation("修改用户：个人中心")
    @PutMapping(value = "center")
    public ResponseEntity<Object> center(@Validated(SellerUser.Update.class) @RequestBody SellerUser resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        SellerDTO sellerDto = sellerService.findByName(SecurityUtils.getUsername());
        if (!resources.getId().equals(sellerDto.getId())) {
            throw new BadRequestException("不能修改他人资料");
        }
        sellerService.updateCenter(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除用户")
    @ApiOperation("删除用户")
    @DeleteMapping
    @PreAuthorize("@el.check('admin','user:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        SellerDTO user = sellerService.findByName(SecurityUtils.getUsername());
        for (Long id : ids) {
            Integer currentLevel = Collections.min(
                    sellerRoleService.findByUsersId(user.getId()).stream().map(SellerRoleSmallDTO::getLevel).collect(
                            Collectors.toList()));
            Integer optLevel = Collections
                    .min(sellerRoleService.findByUsersId(id).stream().map(SellerRoleSmallDTO::getLevel).collect(
                            Collectors.toList()));
            if (currentLevel > optLevel) {
                throw new BadRequestException(
                        "角色权限不足，不能删除：" + sellerService.findByName(SecurityUtils.getUsername()).getUsername());
            }
        }
        sellerService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("修改密码")
    @PostMapping(value = "/updatePass")
    public ResponseEntity<Object> updatePass(@RequestBody StoreUserPassVo passVo) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        // 密码解密
        RSA rsa = new RSA(privateKey, null);
        String oldPass = new String(rsa.decrypt(passVo.getOldPass(), KeyType.PrivateKey));
        String newPass = new String(rsa.decrypt(passVo.getNewPass(), KeyType.PrivateKey));
        SellerDTO user = sellerService.findByName(SecurityUtils.getUsername());
        if (!passwordEncoder.matches(oldPass, user.getPassword())) {
            throw new BadRequestException("修改失败，旧密码错误");
        }
        if (passwordEncoder.matches(newPass, user.getPassword())) {
            throw new BadRequestException("新密码不能与旧密码相同");
        }
        sellerService.updatePass(user.getUsername(), passwordEncoder.encode(newPass));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("修改头像")
    @PostMapping(value = "/updateAvatar")
    public ResponseEntity<Object> updateAvatar(@RequestParam MultipartFile file) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        sellerService.updateAvatar(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("修改邮箱")
    @ApiOperation("修改邮箱")
    @PostMapping(value = "/updateEmail/{code}")
    public ResponseEntity<Object> updateEmail(@PathVariable String code, @RequestBody SellerUser sellerUser) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        // 密码解密
        RSA rsa = new RSA(privateKey, null);
        String password = new String(rsa.decrypt(sellerUser.getPassword(), KeyType.PrivateKey));
        SellerDTO sellerDto = sellerService.findByName(SecurityUtils.getUsername());
        if (!passwordEncoder.matches(password, sellerDto.getPassword())) {
            throw new BadRequestException("密码错误");
        }
        VerificationCode verificationCode = new VerificationCode(code, QshopConstant.RESET_MAIL, "email",
                sellerUser.getEmail());
        verificationCodeService.validated(verificationCode);
        sellerService.updateEmail(sellerDto.getUsername(), sellerUser.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
     *
     * @param resources /
     */
    private void checkLevel(SellerUser resources) {
        SellerDTO user = sellerService.findByName(SecurityUtils.getUsername());
        Integer currentLevel = Collections
                .min(sellerRoleService.findByUsersId(user.getId()).stream().map(SellerRoleSmallDTO::getLevel).collect(
                        Collectors.toList()));
        Integer optLevel = sellerRoleService.findByRoles(resources.getSellerRoles());
        if (currentLevel > optLevel) {
            throw new BadRequestException("角色权限不足");
        }
    }
}
