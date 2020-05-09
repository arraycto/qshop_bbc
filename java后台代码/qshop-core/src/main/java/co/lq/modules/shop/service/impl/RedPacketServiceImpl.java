package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.RedPacket;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.RedPacketRepository;
import co.lq.modules.shop.service.RedPacketService;
import co.lq.modules.shop.service.dto.RedPacketDTO;
import co.lq.modules.shop.service.dto.RedPacketQueryCriteria;
import co.lq.modules.shop.service.mapper.RedPacketMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author billy
* @date 2020-03-27
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RedPacketServiceImpl implements RedPacketService {

    private final RedPacketRepository redPacketRepository;

    private final RedPacketMapper redPacketMapper;

    public RedPacketServiceImpl(RedPacketRepository redPacketRepository, RedPacketMapper redPacketMapper) {
        this.redPacketRepository = redPacketRepository;
        this.redPacketMapper = redPacketMapper;
    }

    @Override
    public Map<String,Object> queryAll(RedPacketQueryCriteria criteria, Pageable pageable){
        Page<RedPacket> page = redPacketRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(redPacketMapper::toDto));
    }

    @Override
    public List<RedPacketDTO> queryAll(RedPacketQueryCriteria criteria){
        return redPacketMapper.toDto(redPacketRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public RedPacketDTO findById(Long id) {
        RedPacket redPacket = redPacketRepository.findById(id).orElseGet(RedPacket::new);
        ValidationUtil.isNull(redPacket.getId(),"RedPacket","id",id);
        return redPacketMapper.toDto(redPacket);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RedPacketDTO create(RedPacket resources) {
        return redPacketMapper.toDto(redPacketRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RedPacket resources) {
        RedPacket redPacket = redPacketRepository.findById(resources.getId()).orElseGet(RedPacket::new);
        ValidationUtil.isNull( redPacket.getId(),"RedPacket","id",resources.getId());
        redPacket.copy(resources);
        redPacketRepository.save(redPacket);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            redPacketRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<RedPacketDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RedPacketDTO redPacket : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("发红包的用户id", redPacket.getUserId());
            map.put("红包金额", redPacket.getAmount());
            map.put("发红包日期", redPacket.getSendDate());
            map.put("红包总数", redPacket.getTotal());
            map.put("单个红包的金额", redPacket.getUnitAmount());
            map.put("红包剩余个数", redPacket.getStock());
            map.put("红包类型", redPacket.getType());
            map.put("备注", redPacket.getNote());
            map.put("所属店铺", redPacket.getStoreId());
            map.put("添加时间", redPacket.getAddTime());
            map.put("更新时间", redPacket.getModifyTime());
            map.put("逻辑删除", redPacket.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}