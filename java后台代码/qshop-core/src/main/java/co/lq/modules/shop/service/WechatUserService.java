package co.lq.modules.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.WechatUser;
import co.lq.modules.shop.service.dto.WechatUserDTO;
import co.lq.modules.shop.service.dto.WechatUserQueryCriteria;

/**
 * @author billy
 * @date 2019-12-13
 */
public interface WechatUserService {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    Map<String, Object> queryAll(WechatUserQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    List<WechatUserDTO> queryAll(WechatUserQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param uid
     * @return
     */
    WechatUserDTO findById(Long uid);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    WechatUserDTO create(WechatUser resources);

    /**
     * 编辑
     *
     * @param resources
     */
    void update(WechatUser resources);

    /**
     * 删除
     *
     * @param uid
     */
    void delete(Long uid);
}
