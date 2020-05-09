package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.SystemGroupData;
import co.lq.modules.shop.web.param.SystemGroupDataQueryParam;
import co.lq.modules.shop.web.vo.SystemGroupDataQueryVo;

/**
 * <p>
 * 组合数据详情表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-19
 */
@Repository
public interface SystemGroupDataMapper extends BaseMapper<SystemGroupData> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SystemGroupDataQueryVo getSystemGroupDataById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param systemGroupDataQueryParam
     * @return
     */
    IPage<SystemGroupDataQueryVo> getSystemGroupDataPageList(@Param("page") Page page,
                                                             @Param("param") SystemGroupDataQueryParam systemGroupDataQueryParam);

}
