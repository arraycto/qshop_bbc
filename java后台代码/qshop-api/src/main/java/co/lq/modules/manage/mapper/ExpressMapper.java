package co.lq.modules.manage.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.manage.entity.Express;
import co.lq.modules.manage.web.param.ExpressQueryParam;
import co.lq.modules.manage.web.vo.ExpressQueryVo;

/**
 * <p>
 * 快递公司表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-12-13
 */
@Repository
public interface ExpressMapper extends BaseMapper<Express> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    ExpressQueryVo getExpressById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param expressQueryParam
     * @return
     */
    IPage<ExpressQueryVo> getExpressPageList(@Param("page") Page page,
                                             @Param("param") ExpressQueryParam expressQueryParam);

}
