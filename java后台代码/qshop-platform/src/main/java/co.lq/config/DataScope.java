package co.lq.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import co.lq.modules.system.domain.PlatformDept;
import co.lq.modules.system.service.PlatformDeptService;
import co.lq.modules.system.service.PlatformRoleService;
import co.lq.modules.system.service.PlatformUserService;
import co.lq.modules.system.service.dto.PlatformUserDTO;
import co.lq.modules.system.service.dto.RoleSmallDTO;
import co.lq.utils.SecurityUtils;

/**
 * 数据权限配置
 *
 * @author billy
 * @date 2019-4-1
 */
@Component
public class DataScope {

    private final String[]            scopeType = { "全部", "本级", "自定义" };

    private final PlatformUserService platformUserService;

    private final PlatformRoleService platformRoleService;

    private final PlatformDeptService platformDeptService;

    public DataScope(PlatformUserService platformUserService, PlatformRoleService platformRoleService,
                     PlatformDeptService platformDeptService) {
        this.platformUserService = platformUserService;
        this.platformRoleService = platformRoleService;
        this.platformDeptService = platformDeptService;
    }

    public Set<Long> getDeptIds() {

        PlatformUserDTO user = platformUserService.findByName(SecurityUtils.getUsername());

        // 用于存储部门id
        Set<Long> deptIds = new HashSet<>();

        // 查询用户角色
        List<RoleSmallDTO> roleSet = platformRoleService.findByUsersId(user.getId());

        for (RoleSmallDTO role : roleSet) {

            if (scopeType[0].equals(role.getDataScope())) {
                return new HashSet<>();
            }

            // 存储本级的数据权限
            if (scopeType[1].equals(role.getDataScope())) {
                deptIds.add(user.getPlatformDept().getId());
            }

            // 存储自定义的数据权限
            if (scopeType[2].equals(role.getDataScope())) {
                Set<PlatformDept> depts = platformDeptService.findByRoleIds(role.getId());
                for (PlatformDept dept : depts) {
                    deptIds.add(dept.getId());
                    List<PlatformDept> deptChildren = platformDeptService.findByPid(dept.getId());
                    if (deptChildren != null && deptChildren.size() != 0) {
                        deptIds.addAll(getDeptChildren(deptChildren));
                    }
                }
            }
        }
        return deptIds;
    }

    public List<Long> getDeptChildren(List<PlatformDept> deptList) {
        List<Long> list = new ArrayList<>();
        deptList.forEach(dept -> {
            if (dept != null && dept.getEnabled()) {
                List<PlatformDept> depts = platformDeptService.findByPid(dept.getId());
                if (deptList.size() != 0) {
                    list.addAll(getDeptChildren(depts));
                }
                list.add(dept.getId());
            }
        });
        return list;
    }
}
