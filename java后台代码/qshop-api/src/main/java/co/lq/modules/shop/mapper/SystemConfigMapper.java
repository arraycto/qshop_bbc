package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.SystemConfig;
import co.lq.modules.shop.web.param.SystemConfigQueryParam;
import co.lq.modules.shop.web.vo.SystemConfigQueryVo;

/**
 * <p>
 * 配置表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-19
 */
@Repository
public interface SystemConfigMapper extends BaseMapper<SystemConfig> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SystemConfigQueryVo getSystemConfigById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param systemConfigQueryParam
     * @return
     */
    IPage<SystemConfigQueryVo> getSystemConfigPageList(@Param("page") Page page,
                                                       @Param("param") SystemConfigQueryParam systemConfigQueryParam);

}
