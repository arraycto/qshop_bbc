package co.lq.modules.user.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.user.entity.WechatUser;
import co.lq.modules.user.web.param.WechatUserQueryParam;
import co.lq.modules.user.web.vo.WechatUserQueryVo;

/**
 * <p>
 * 微信用户表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
@Repository
public interface WechatUserMapper extends BaseMapper<WechatUser> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    WechatUserQueryVo getWechatUserById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param wechatUserQueryParam
     * @return
     */
    IPage<WechatUserQueryVo> getWechatUserPageList(@Param("page") Page page,
                                                   @Param("param") WechatUserQueryParam wechatUserQueryParam);

}
