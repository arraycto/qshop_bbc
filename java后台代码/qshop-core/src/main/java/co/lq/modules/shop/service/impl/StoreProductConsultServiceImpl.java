package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.StoreProductConsult;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.StoreProductConsultRepository;
import co.lq.modules.shop.service.StoreProductConsultService;
import co.lq.modules.shop.service.dto.StoreProductConsultDTO;
import co.lq.modules.shop.service.dto.StoreProductConsultQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreProductConsultMapper;
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
public class StoreProductConsultServiceImpl implements StoreProductConsultService {

    private final StoreProductConsultRepository storeProductConsultRepository;

    private final StoreProductConsultMapper storeProductConsultMapper;

    public StoreProductConsultServiceImpl(StoreProductConsultRepository storeProductConsultRepository, StoreProductConsultMapper storeProductConsultMapper) {
        this.storeProductConsultRepository = storeProductConsultRepository;
        this.storeProductConsultMapper = storeProductConsultMapper;
    }

    @Override
    public Map<String,Object> queryAll(StoreProductConsultQueryCriteria criteria, Pageable pageable){
        Page<StoreProductConsult> page = storeProductConsultRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(storeProductConsultMapper::toDto));
    }

    @Override
    public List<StoreProductConsultDTO> queryAll(StoreProductConsultQueryCriteria criteria){
        return storeProductConsultMapper.toDto(storeProductConsultRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public StoreProductConsultDTO findById(Long id) {
        StoreProductConsult storeProductConsult = storeProductConsultRepository.findById(id).orElseGet(StoreProductConsult::new);
        ValidationUtil.isNull(storeProductConsult.getId(),"StoreProductConsult","id",id);
        return storeProductConsultMapper.toDto(storeProductConsult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreProductConsultDTO create(StoreProductConsult resources) {
        return storeProductConsultMapper.toDto(storeProductConsultRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreProductConsult resources) {
        StoreProductConsult storeProductConsult = storeProductConsultRepository.findById(resources.getId()).orElseGet(StoreProductConsult::new);
        ValidationUtil.isNull( storeProductConsult.getId(),"StoreProductConsult","id",resources.getId());
        storeProductConsult.copy(resources);
        storeProductConsultRepository.save(storeProductConsult);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            storeProductConsultRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StoreProductConsultDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StoreProductConsultDTO storeProductConsult : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("商品编号", storeProductConsult.getProductId());
            map.put("商品名称", storeProductConsult.getProductName());
            map.put("咨询发布者会员编号(0：游客)", storeProductConsult.getUid());
            map.put("会员名称", storeProductConsult.getMemberName());
            map.put("店铺编号", storeProductConsult.getStoreId());
            map.put("咨询发布者邮箱", storeProductConsult.getEmail());
            map.put("咨询内容", storeProductConsult.getConsultContent());
            map.put("咨询添加时间", storeProductConsult.getConsultAddtime());
            map.put("咨询回复内容", storeProductConsult.getConsultReply());
            map.put("咨询回复时间", storeProductConsult.getConsultReplyTime());
            map.put("0表示不匿名 1表示匿名", storeProductConsult.getIsanonymous());
            map.put(" isDel",  storeProductConsult.getIsDel());
            map.put(" pic",  storeProductConsult.getPic());
            map.put(" attr",  storeProductConsult.getAttr());
            map.put(" stars",  storeProductConsult.getStars());
            map.put(" oid",  storeProductConsult.getOid());
            map.put("1 商品 2 订单", storeProductConsult.getType());
            map.put("添加时间", storeProductConsult.getAddTime());
            map.put("更新时间", storeProductConsult.getModifyTime());
            map.put("逻辑删除", storeProductConsult.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}