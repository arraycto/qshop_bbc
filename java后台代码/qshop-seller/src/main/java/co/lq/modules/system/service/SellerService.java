package co.lq.modules.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import co.lq.modules.system.domain.SellerUser;
import co.lq.modules.system.service.dto.SellerDTO;
import co.lq.modules.system.service.dto.SellerQueryCriteria;

/**
 * @author billy
 * @date 2018-11-23
 */
public interface SellerService {

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return /
     */
    SellerDTO findById(long id);

    /**
     * 新增用户
     *
     * @param resources /
     * @return /
     */
    SellerDTO create(SellerUser resources);

    /**
     * 编辑用户
     *
     * @param resources /
     */
    void update(SellerUser resources);

    /**
     * 删除用户
     *
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 根据用户名查询
     *
     * @param userName /
     * @return /
     */
    SellerDTO findByName(String userName);

    /**
     * 修改密码
     *
     * @param username 用户名
     * @param encryptPassword 密码
     */
    void updatePass(String username, String encryptPassword);

    /**
     * 修改头像
     *
     * @param file 文件
     */
    void updateAvatar(MultipartFile file);

    /**
     * 修改邮箱
     *
     * @param username 用户名
     * @param email 邮箱
     */
    void updateEmail(String username, String email);

    /**
     * 查询全部
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(SellerQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部不分页
     *
     * @param criteria 条件
     * @return /
     */
    List<SellerDTO> queryAll(SellerQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<SellerDTO> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 用户自助修改资料
     *
     * @param resources /
     */
    void updateCenter(SellerUser resources);
}
