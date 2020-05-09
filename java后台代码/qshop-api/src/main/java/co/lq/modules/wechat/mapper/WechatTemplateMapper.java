package co.lq.modules.wechat.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.wechat.entity.WechatTemplate;
import co.lq.modules.wechat.web.param.WechatTemplateQueryParam;
import co.lq.modules.wechat.web.vo.WechatTemplateQueryVo;

/**
 * <p>
 * 微信模板 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-12-10
 */
@Repository
public interface WechatTemplateMapper extends BaseMapper<WechatTemplate> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    WechatTemplateQueryVo getWechatTemplateById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param wechatTemplateQueryParam
     * @return
     */
    IPage<WechatTemplateQueryVo> getWechatTemplatePageList(@Param("page") Page page,
                                                           @Param("param") WechatTemplateQueryParam wechatTemplateQueryParam);

}
