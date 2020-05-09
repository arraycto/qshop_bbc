package co.lq.modules.activity.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.ObjectUtil;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.activity.entity.StoreBargainUser;
import co.lq.modules.activity.entity.StoreBargainUserHelp;
import co.lq.modules.activity.mapper.StoreBargainUserHelpMapper;
import co.lq.modules.activity.mapping.StoreBargainHelpMap;
import co.lq.modules.activity.service.ApiStoreBargainUserHelpService;
import co.lq.modules.activity.service.ApiStoreBargainUserService;
import co.lq.modules.activity.web.param.StoreBargainUserHelpQueryParam;
import co.lq.modules.activity.web.vo.StoreBargainUserHelpQueryVo;
import co.lq.modules.user.service.UserService;
import co.lq.modules.user.web.vo.UserQueryVo;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 砍价用户帮助表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-12-21
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiStoreBargainUserHelpServiceImpl extends
        BaseServiceImpl<StoreBargainUserHelpMapper, StoreBargainUserHelp> implements ApiStoreBargainUserHelpService {

    @Autowired
    private StoreBargainUserHelpMapper storeBargainUserHelpMapper;

    @Autowired
    private ApiStoreBargainUserService apiStoreBargainUserService;
    @Autowired
    private UserService                userService;

    @Autowired
    private StoreBargainHelpMap        storeBargainHelpMap;

    /**
     * 获取砍价帮
     *
     * @param bargainId
     * @param bargainUserUid
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<StoreBargainUserHelpQueryVo> getList(long bargainId, long bargainUserUid, int page, int limit) {

        StoreBargainUser storeBargainUser = apiStoreBargainUserService.getBargainUserInfo(bargainId, bargainUserUid);
        if (ObjectUtil.isNull(storeBargainUser)) {
            return new ArrayList<>();
        }
        Page<StoreBargainUserHelp> pageModel = new Page<>(page, limit);
        QueryWrapper<StoreBargainUserHelp> wrapper = new QueryWrapper<>();
        wrapper.eq("bargain_user_id", storeBargainUser.getId()).orderByDesc("id");

        List<StoreBargainUserHelpQueryVo> storeBargainUserHelpQueryVos = storeBargainHelpMap
                .toDto(storeBargainUserHelpMapper.selectPage(pageModel, wrapper).getRecords());

        storeBargainUserHelpQueryVos.forEach(item -> {
            UserQueryVo userQueryVo = userService.getUserById(item.getUid());
            item.setAvatar(userQueryVo.getAvatar());
            item.setNickname(userQueryVo.getNickname());
        });

        return storeBargainUserHelpQueryVos;
    }

    /**
     * 获取砍价帮总人数
     *
     * @param bargainId 砍价产品ID
     * @param bargainUserUid 用户参与砍价表id
     * @return
     */
    @Override
    public int getBargainUserHelpPeopleCount(long bargainId, long bargainUserUid) {
        return storeBargainUserHelpMapper
                .selectCount(new QueryWrapper<StoreBargainUserHelp>().eq("bargain_id", bargainId).eq("bargain_user_id",
                        bargainUserUid));
    }

    @Override
    public StoreBargainUserHelpQueryVo getStoreBargainUserHelpById(Serializable id) throws Exception {
        return storeBargainUserHelpMapper.getStoreBargainUserHelpById(id);
    }

    @Override
    public Paging<StoreBargainUserHelpQueryVo> getStoreBargainUserHelpPageList(StoreBargainUserHelpQueryParam storeBargainUserHelpQueryParam)
            throws Exception {
        Page page = setPageParam(storeBargainUserHelpQueryParam, OrderItem.desc("create_time"));
        IPage<StoreBargainUserHelpQueryVo> iPage = storeBargainUserHelpMapper.getStoreBargainUserHelpPageList(page,
                storeBargainUserHelpQueryParam);
        return new Paging(iPage);
    }

}
