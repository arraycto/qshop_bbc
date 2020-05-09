package co.lq.modules.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import co.lq.modules.system.domain.SellerDept;
import co.lq.modules.system.service.dto.SellerDeptDTO;
import co.lq.modules.system.service.dto.SellerDeptQueryCriteria;

/**
 * @author billy
 * @date 2019-03-25
 */
public interface SellerDeptService {

    /**
     * 查询所有数据
     *
     * @param criteria 条件
     * @return /
     */
    List<SellerDeptDTO> queryAll(SellerDeptQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    SellerDeptDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    SellerDeptDTO create(SellerDept resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(SellerDept resources);

    /**
     * 删除
     *
     * @param sellerDeptDtos /
     */
    void delete(Set<SellerDeptDTO> sellerDeptDtos);

    /**
     * 构建树形数据
     *
     * @param sellerDeptDtos 原始数据
     * @return /
     */
    Object buildTree(List<SellerDeptDTO> sellerDeptDtos);

    /**
     * 根据PID查询
     *
     * @param pid /
     * @return /
     */
    List<SellerDept> findByPid(long pid);

    /**
     * 根据PID查询
     *
     * @param pid /
     * @param storeId /
     * @return /
     */
    List<SellerDept> findByPidAndStoreId(long pid, Long storeId);

    /**
     * 根据角色ID查询
     *
     * @param id /
     * @return /
     */
    Set<SellerDept> findByRoleIds(Long id);

    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<SellerDeptDTO> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 获取待删除的部门
     *
     * @param deptList /
     * @param sellerDeptDtos /
     * @return /
     */
    Set<SellerDeptDTO> getDeleteDepts(List<SellerDept> deptList, Set<SellerDeptDTO> sellerDeptDtos);
}
