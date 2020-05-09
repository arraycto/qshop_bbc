package co.lq.modules.user.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.user.entity.UserExtract;
import co.lq.modules.user.web.param.UserExtractQueryParam;
import co.lq.modules.user.web.vo.UserExtractQueryVo;

/**
 * <p>
 * 用户提现表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-11-11
 */
@Repository
public interface UserExtractMapper extends BaseMapper<UserExtract> {

    @Select("select IFNULL(sum(extract_price),0) from user_extract " + "where status=1 " + "and uid=#{uid}")
    double sumPrice(@Param("uid") long uid);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserExtractQueryVo getUserExtractById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param userExtractQueryParam
     * @return
     */
    IPage<UserExtractQueryVo> getUserExtractPageList(@Param("page") Page page,
                                                     @Param("param") UserExtractQueryParam userExtractQueryParam);

}
