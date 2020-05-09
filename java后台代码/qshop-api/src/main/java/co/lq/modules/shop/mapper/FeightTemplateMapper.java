package co.lq.modules.shop.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import co.lq.modules.shop.entity.FeightTemplate;
import co.lq.modules.shop.web.param.FeightTemplateQueryParam;
import co.lq.modules.shop.web.vo.FeightTemplateQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <p>
 * 运费模版 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2020-05-03
 */
@Repository
public interface FeightTemplateMapper extends BaseMapper<FeightTemplate> {

    /**
     * 根据ID获取查询对象
     * @param id
     * @return
     */
    FeightTemplateQueryVo getFeightTemplateById(Serializable id);

    /**
     * 获取分页对象
     * @param page
     * @param feightTemplateQueryParam
     * @return
     */
    IPage<FeightTemplateQueryVo> getFeightTemplatePageList(@Param("page") Page page, @Param("param") FeightTemplateQueryParam feightTemplateQueryParam);

}
