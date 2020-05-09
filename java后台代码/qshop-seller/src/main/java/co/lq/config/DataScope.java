package co.lq.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import co.lq.modules.system.domain.SellerDept;
import co.lq.modules.system.service.SellerDeptService;
import co.lq.modules.system.service.SellerRoleService;
import co.lq.modules.system.service.SellerService;
import co.lq.modules.system.service.dto.SellerDTO;
import co.lq.modules.system.service.dto.SellerRoleSmallDTO;
import co.lq.utils.SecurityUtils;

/**
 * 数据权限配置
 *
 * @author billy
 * @date 2019-4-1
 */
@Component
public class DataScope {

    private final String[]          scopeType = { "全部", "本级", "自定义" };

    private final SellerService     sellerService;

    private final SellerRoleService sellerRoleService;

    private final SellerDeptService sellerDeptService;

    public DataScope(SellerService sellerService, SellerRoleService sellerRoleService,
                     SellerDeptService sellerDeptService) {
        this.sellerService = sellerService;
        this.sellerRoleService = sellerRoleService;
        this.sellerDeptService = sellerDeptService;
    }

    public Set<Long> getDeptIds() {

        SellerDTO user = sellerService.findByName(SecurityUtils.getUsername());

        // 用于存储部门id
        Set<Long> deptIds = new HashSet<>();

        // 查询用户角色
        List<SellerRoleSmallDTO> roleSet = sellerRoleService.findByUsersId(user.getId());

        for (SellerRoleSmallDTO role : roleSet) {

            if (scopeType[0].equals(role.getDataScope())) {
                return new HashSet<>();
            }

            // 存储本级的数据权限
            if (scopeType[1].equals(role.getDataScope())) {
                deptIds.add(user.getSellerDept().getId());
            }

            // 存储自定义的数据权限
            if (scopeType[2].equals(role.getDataScope())) {
                Set<SellerDept> depts = sellerDeptService.findByRoleIds(role.getId());
                for (SellerDept dept : depts) {
                    deptIds.add(dept.getId());
                    List<SellerDept> deptChildren = sellerDeptService.findByPidAndStoreId(dept.getId(),
                            user.getStoreId());
                    if (deptChildren != null && deptChildren.size() != 0) {
                        deptIds.addAll(getDeptChildren(deptChildren));
                    }
                }
            }
        }
        return deptIds;
    }

    public List<Long> getDeptChildren(List<SellerDept> deptList) {
        List<Long> list = new ArrayList<>();
        deptList.forEach(dept -> {
            if (dept != null && dept.getEnabled()) {
                List<SellerDept> depts = sellerDeptService.findByPid(dept.getId());
                if (deptList.size() != 0) {
                    list.addAll(getDeptChildren(depts));
                }
                list.add(dept.getId());
            }
        });
        return list;
    }
}
