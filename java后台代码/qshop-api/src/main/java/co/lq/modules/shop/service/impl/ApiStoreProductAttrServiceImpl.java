package co.lq.modules.shop.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.modules.shop.entity.StoreProductAttr;
import co.lq.modules.shop.entity.StoreProductAttrValue;
import co.lq.modules.shop.mapper.StoreProductAttrMapper;
import co.lq.modules.shop.mapper.StoreProductAttrValueMapper;
import co.lq.modules.shop.mapping.ProductAttrMap;
import co.lq.modules.shop.service.ApiStoreProductAttrService;
import co.lq.modules.shop.web.dto.AttrValueDTO;
import co.lq.modules.shop.web.vo.StoreProductAttrQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 商品属性表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiStoreProductAttrServiceImpl extends BaseServiceImpl<StoreProductAttrMapper, StoreProductAttr>
        implements ApiStoreProductAttrService {

    private final StoreProductAttrMapper      storeProductAttrMapper;
    private final StoreProductAttrValueMapper storeProductAttrValueMapper;

    private final ProductAttrMap              productAttrMap;

    @Override
    public void incProductAttrStock(int num, long productId, String unique) {
        storeProductAttrValueMapper.incStockDecSales(num, productId, unique);
    }

    @Override
    public void decProductAttrStock(int num, long productId, String unique) {
        storeProductAttrValueMapper.decStockIncSales(num, productId, unique);
    }

    @Override
    public Map<String, Object> getProductAttrDetail(long productId, int uid, int type) {
        QueryWrapper<StoreProductAttr> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id", productId).eq("deleted", 0).eq("type", 0).orderByAsc("attr_values");
        List<StoreProductAttr> storeProductAttrs = storeProductAttrMapper.selectList(wrapper);

        QueryWrapper<StoreProductAttrValue> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("product_id", productId).eq("deleted", 0);
        List<StoreProductAttrValue> productAttrValues = storeProductAttrValueMapper.selectList(wrapper2);

        List<Map<String, StoreProductAttrValue>> mapList = new ArrayList<>();

        Map<String, StoreProductAttrValue> map = new LinkedHashMap<>();
        for (StoreProductAttrValue value : productAttrValues) {

            map.put(value.getSuk(), value);
            // mapList.add(map);
        }

        List<StoreProductAttrQueryVo> storeProductAttrQueryVoList = new ArrayList<>();

        for (StoreProductAttr attr : storeProductAttrs) {
            List<String> stringList = Arrays.asList(attr.getAttrValues().split(","));
            List<AttrValueDTO> attrValueDTOS = new ArrayList<>();
            for (String str : stringList) {
                AttrValueDTO attrValueDTO = new AttrValueDTO();
                attrValueDTO.setAttr(str);
                attrValueDTO.setCheck(false);

                attrValueDTOS.add(attrValueDTO);
            }
            StoreProductAttrQueryVo attrQueryVo = productAttrMap.toDto(attr);
            attrQueryVo.setAttrValue(attrValueDTOS);
            attrQueryVo.setAttrValueArr(stringList);

            storeProductAttrQueryVoList.add(attrQueryVo);

        }

        //System.out.println(storeProductAttrQueryVoList);
        //System.out.println(map);
        Map<String, Object> returnMap = new LinkedHashMap<>();

        returnMap.put("productAttr", storeProductAttrQueryVoList);
        returnMap.put("productValue", map);
        return returnMap;
    }

    /**
     * 库存
     *
     * @param unique
     * @return
     */
    @Override
    public int uniqueByStock(String unique) {
        QueryWrapper<StoreProductAttrValue> wrapper = new QueryWrapper<>();
        wrapper.eq("`unique`", unique);
        return storeProductAttrValueMapper.selectOne(wrapper).getStock();
    }

    @Override
    public Boolean issetProductUnique(long productId, String unique) {
        return null;
    }

    @Override
    public StoreProductAttrValue uniqueByAttrInfo(String unique) {
        QueryWrapper<StoreProductAttrValue> wrapper = new QueryWrapper<>();
        wrapper.eq("`unique`", unique);
        return storeProductAttrValueMapper.selectOne(wrapper);
    }
}
