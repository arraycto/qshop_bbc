package co.lq.modules.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import co.lq.modules.system.domain.PlatformDept;
import co.lq.modules.system.service.dto.PlatformDeptDTO;
import co.lq.modules.system.service.dto.PlatformDeptQueryCriteria;

/**
 * @author billy
 * @date 2019-03-25
 */
public interface PlatformDeptService {

    /**
     * 查询所有数据
     *
     * @param criteria 条件
     * @return /
     */
    List<PlatformDeptDTO> queryAll(PlatformDeptQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    PlatformDeptDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    PlatformDeptDTO create(PlatformDept resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(PlatformDept resources);

    /**
     * 删除
     *
     * @param platformDeptDtos /
     */
    void delete(Set<PlatformDeptDTO> platformDeptDtos);

    /**
     * 构建树形数据
     *
     * @param platformDeptDtos 原始数据
     * @return /
     */
    Object buildTree(List<PlatformDeptDTO> platformDeptDtos);

    /**
     * 根据PID查询
     *
     * @param pid /
     * @return /
     */
    List<PlatformDept> findByPid(long pid);

    /**
     * 根据角色ID查询
     *
     * @param id /
     * @return /
     */
    Set<PlatformDept> findByRoleIds(Long id);

    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<PlatformDeptDTO> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 获取待删除的部门
     *
     * @param platformDeptList /
     * @param platformDeptDtos /
     * @return /
     */
    Set<PlatformDeptDTO> getDeleteDepts(List<PlatformDept> platformDeptList, Set<PlatformDeptDTO> platformDeptDtos);
}
