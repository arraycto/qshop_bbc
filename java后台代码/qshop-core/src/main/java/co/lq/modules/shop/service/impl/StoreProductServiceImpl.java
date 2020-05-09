package co.lq.modules.shop.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.exception.BadRequestException;
import co.lq.modules.shop.domain.Catalog;
import co.lq.modules.shop.domain.CmsPrefrenceAreaProductRelation;
import co.lq.modules.shop.domain.CmsSubjectProductRelation;
import co.lq.modules.shop.domain.StoreCategory;
import co.lq.modules.shop.domain.StoreProduct;
import co.lq.modules.shop.domain.StoreProductAttr;
import co.lq.modules.shop.domain.StoreProductAttrResult;
import co.lq.modules.shop.domain.StoreProductAttrValue;
import co.lq.modules.shop.domain.UserLevel;
import co.lq.modules.shop.repository.CatalogRepository;
import co.lq.modules.shop.repository.CategoryRepository;
import co.lq.modules.shop.repository.CmsPrefrenceAreaProductRelationRepository;
import co.lq.modules.shop.repository.CmsSubjectProductRelationRepository;
import co.lq.modules.shop.repository.StoreCategoryRepository;
import co.lq.modules.shop.repository.StoreProductAttrRepository;
import co.lq.modules.shop.repository.StoreProductAttrResultRepository;
import co.lq.modules.shop.repository.StoreProductAttrValueRepository;
import co.lq.modules.shop.repository.StoreProductRepository;
import co.lq.modules.shop.repository.UserLevelRepository;
import co.lq.modules.shop.service.StoreProductService;
import co.lq.modules.shop.service.dto.DetailDTO;
import co.lq.modules.shop.service.dto.FromatDetailDTO;
import co.lq.modules.shop.service.dto.ProductFormatDTO;
import co.lq.modules.shop.service.dto.StoreProductDTO;
import co.lq.modules.shop.service.dto.StoreProductQueryCriteria;
import co.lq.modules.shop.service.dto.UserLevelQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreProductMapper;
import co.lq.modules.shop.service.param.MemberPriceParam;
import co.lq.modules.shop.service.param.PrefrenceAreaProductRelationParam;
import co.lq.modules.shop.service.param.ProductAttributeValueParam;
import co.lq.modules.shop.service.param.SkuStockParam;
import co.lq.modules.shop.service.param.SubjectProductRelationParam;
import co.lq.utils.OrderUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.StringUtils;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-10-04
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreProductServiceImpl implements StoreProductService {

    private final StoreProductRepository                    storeProductRepository;
    private final StoreProductAttrRepository                storeProductAttrRepository;
    private final StoreProductAttrValueRepository           storeProductAttrValueRepository;
    private final StoreProductAttrResultRepository          storeProductAttrResultRepository;
    private final UserLevelRepository                       userLevelRepository;
    private final CatalogRepository                         catalogRepository;
    private final CategoryRepository                        categoryRepository;
    private final StoreCategoryRepository                   storeCategoryRepository;
    private final CmsSubjectProductRelationRepository       cmsSubjectProductRelationRepository;
    private final CmsPrefrenceAreaProductRelationRepository cmsPrefrenceAreaProductRelationRepository;

    private final StoreProductMapper                        storeProductMapper;

    public StoreProductServiceImpl(StoreProductRepository storeProductRepository,
                                   StoreProductAttrRepository storeProductAttrRepository,
                                   StoreProductAttrValueRepository storeProductAttrValueRepository,
                                   StoreProductAttrResultRepository storeProductAttrResultRepository,
                                   UserLevelRepository userLevelRepository, CatalogRepository catalogRepository,
                                   CategoryRepository categoryRepository,
                                   StoreCategoryRepository storeCategoryRepository,
                                   CmsSubjectProductRelationRepository cmsSubjectProductRelationRepository,
                                   CmsPrefrenceAreaProductRelationRepository cmsPrefrenceAreaProductRelationRepository,
                                   StoreProductMapper storeProductMapper) {
        this.storeProductRepository = storeProductRepository;
        this.storeProductAttrRepository = storeProductAttrRepository;
        this.storeProductAttrValueRepository = storeProductAttrValueRepository;
        this.storeProductAttrResultRepository = storeProductAttrResultRepository;
        this.userLevelRepository = userLevelRepository;
        this.catalogRepository = catalogRepository;
        this.categoryRepository = categoryRepository;
        this.storeCategoryRepository = storeCategoryRepository;
        this.cmsSubjectProductRelationRepository = cmsSubjectProductRelationRepository;
        this.cmsPrefrenceAreaProductRelationRepository = cmsPrefrenceAreaProductRelationRepository;
        this.storeProductMapper = storeProductMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreProductQueryCriteria criteria, Pageable pageable) {
        criteria.setIsDel(0);
        Page<StoreProduct> page = storeProductRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        List<StoreProductDTO> storeProductDTOS = new ArrayList<>();

        for (StoreProduct product : page.getContent()) {
            StoreProductDTO storeProductDTO = storeProductMapper.toDto(product);
            //规格属性库存
            Integer newStock = storeProductAttrValueRepository.sumStock(product.getId());
            if (newStock != null) {
                storeProductDTO.setStock(newStock);
            }
            storeProductDTOS.add(storeProductDTO);
        }
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", storeProductDTOS);
        map.put("totalElements", page.getTotalElements());
        return map;
    }

    @Override
    public List<StoreProductDTO> queryAll(StoreProductQueryCriteria criteria) {
        return storeProductMapper.toDto(storeProductRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreProductDTO findById(Long id) {
        Optional<StoreProduct> storeProduct = storeProductRepository.findById(id);
        ValidationUtil.isNull(storeProduct, "StoreProduct", "id", id);
        UserLevelQueryCriteria criteriaUserLevel = new UserLevelQueryCriteria();
        List<UserLevel> userLevels = userLevelRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp
                .getPredicate(root, criteriaUserLevel, criteriaBuilder));
        List<MemberPriceParam> memberPriceParamList = userLevels.stream().map(userLevel -> {
            MemberPriceParam memberPriceParam = new MemberPriceParam();
            memberPriceParam.setMemberLevelId(userLevel.getId());
            memberPriceParam.setMemberLevelName(userLevel.getName());

            return memberPriceParam;
        }).collect(Collectors.toList());
        StoreProductDTO storeProductDTO = storeProductMapper.toDto(storeProduct.get());
        storeProductDTO.setMemberPriceList(memberPriceParamList);

        List<StoreProductAttr> storeProductAttrList = storeProductAttrRepository.findAllByProductIdAndDeleted(id, 0);
        if (storeProductAttrList.size() > 0) {
            List<ProductAttributeValueParam> productAttributeValueParams = storeProductAttrList.stream()
                    .map(storeProductAttr -> {
                        ProductAttributeValueParam productAttributeValueParam = new ProductAttributeValueParam();
                        productAttributeValueParam.setName(storeProductAttr.getAttrName());
                        productAttributeValueParam.setProductAttributeId(storeProductAttr.getCatalogAttrId());
                        productAttributeValueParam.setType(storeProductAttr.getType());
                        productAttributeValueParam.setValue(storeProductAttr.getAttrValues());

                        return productAttributeValueParam;
                    })
                    .collect(Collectors.toList());
            storeProductDTO.setProductAttributeValueList(productAttributeValueParams);
        }

        List<StoreProductAttrValue> storeProductAttrValueList = storeProductAttrValueRepository
                .findAllByProductIdAndDeleted(id, 0);
        if (storeProductAttrValueList.size() > 0) {
            List<SkuStockParam> skuStockParamList = storeProductAttrValueList.stream().map(storeProductAttrValue -> {
                SkuStockParam skuStockParam = new SkuStockParam();
                skuStockParam.setBarCode(storeProductAttrValue.getBarCode());
                skuStockParam.setCost(storeProductAttrValue.getCost());
                skuStockParam.setPrice(storeProductAttrValue.getPrice());
                skuStockParam.setSales(storeProductAttrValue.getSales());
                skuStockParam.setStock(storeProductAttrValue.getStock());
                String[] skuList = storeProductAttrValue.getSuk().split(",");
                if (storeProductAttrValue.getImage() != null) {
                    skuStockParam.setPic(StringUtils.split(storeProductAttrValue.getImage()));
                } else {
                    skuStockParam.setPic(new String[0]);
                }
                if (skuList.length == 1) {
                    skuStockParam.setSp1(skuList[0]);
                } else if (skuList.length == 2) {
                    skuStockParam.setSp1(skuList[0]);
                    skuStockParam.setSp2(skuList[1]);
                } else {
                    skuStockParam.setSp1(skuList[0]);
                    skuStockParam.setSp2(skuList[1]);
                    skuStockParam.setSp3(skuList[2]);
                }
                return skuStockParam;
            }).collect(Collectors.toList());
            storeProductDTO.setSkuStockList(skuStockParamList);
        }

        if (storeProductDTO.getCatalogId() != null && storeProductDTO.getCatalogId() > 0) {
            Optional<Catalog> optionalCatalog = catalogRepository.findById(storeProductDTO.getCatalogId());
            Catalog catalog = optionalCatalog.get();
            List<Long> catalogList = new ArrayList<>();
            catalogList.add(catalog.getId());
            while (catalog.getPid() > 0) {
                optionalCatalog = catalogRepository.findById(catalog.getPid());
                catalog = optionalCatalog.get();
                catalogList.add(catalog.getId());
            }
            Collections.reverse(catalogList);
            storeProductDTO.setCategoryList(catalogList);
        }

        if (storeProductDTO.getStoreCateId() != null && storeProductDTO.getStoreCateId() > 0) {
            Optional<StoreCategory> optionalCategory = storeCategoryRepository
                    .findById(storeProductDTO.getStoreCateId());
            StoreCategory category = optionalCategory.get();
            List<Long> categoryList = new ArrayList<>();
            categoryList.add(category.getId());
            while (category.getPid() > 0) {
                optionalCategory = storeCategoryRepository.findById(category.getPid());
                category = optionalCategory.get();
                categoryList.add(category.getId());
            }
            Collections.reverse(categoryList);
            storeProductDTO.setStoreCategoryList(categoryList);
        }
        List<String> galleryList = new ArrayList<>();
        if (storeProductDTO.getImage() != null && !"".equals(storeProductDTO.getImage())) {
            galleryList.add(storeProductDTO.getImage());
        }
        if (storeProductDTO.getSliderImage() != null && !"".equals(storeProductDTO.getSliderImage())) {
            String[] sliderImages = storeProductDTO.getSliderImage().split(",");
            for (int i = 0; i < sliderImages.length; i++) {
                galleryList.add(sliderImages[i]);
            }
        }
        storeProductDTO.setGalleryList(galleryList);

        List<SubjectProductRelationParam> subjectProductRelationParamList = new ArrayList<>();
        List<CmsSubjectProductRelation> cmsSubjectProductRelationList = cmsSubjectProductRelationRepository
                .findByProductIdAndDeleted(id, 0);
        if (cmsSubjectProductRelationList.size() > 0) {
            subjectProductRelationParamList = cmsSubjectProductRelationList.stream().map(cmsSubjectProductRelation -> {
                SubjectProductRelationParam subjectProductRelationParam = new SubjectProductRelationParam();
                subjectProductRelationParam.setSubjectId(cmsSubjectProductRelation.getSubjectId());
                return subjectProductRelationParam;
            }).collect(Collectors.toList());
        }
        storeProductDTO.setSubjectProductRelationList(subjectProductRelationParamList);

        List<PrefrenceAreaProductRelationParam> prefrenceAreaProductRelationList = new ArrayList<>();
        List<CmsPrefrenceAreaProductRelation> cmsPrefrenceAreaProductRelationList = cmsPrefrenceAreaProductRelationRepository
                .findByProductIdAndDeleted(id, 0);
        if (cmsPrefrenceAreaProductRelationList.size() > 0) {
            prefrenceAreaProductRelationList = cmsPrefrenceAreaProductRelationList.stream()
                    .map(cmsPrefrenceAreaProductRelation -> {
                        PrefrenceAreaProductRelationParam prefrenceAreaProductRelationParam = new PrefrenceAreaProductRelationParam();
                        prefrenceAreaProductRelationParam
                                .setPrefrenceAreaId(cmsPrefrenceAreaProductRelation.getPrefrenceAreaId());
                        return prefrenceAreaProductRelationParam;
                    })
                    .collect(Collectors.toList());
        }
        storeProductDTO.setPrefrenceAreaProductRelationList(prefrenceAreaProductRelationList);

        return storeProductDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreProductDTO create(StoreProduct resources) {
        resources.setVerifyStatus(0);
        resources.setCodePath("");
        return storeProductMapper.toDto(storeProductRepository.save(resources));
    }

    @Override
    public void saveProductAttributes(Long productId, StoreProduct resources) {
        //通过商品类目id获取商品的规格和属性
        final Integer[] index = { 0 };
        List<SkuStockParam> skuList = resources.getSkuStockList();
        List<ProductAttributeValueParam> productAttributeValueList = resources.getProductAttributeValueList();
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (skuList != null && skuList.size() > 0) {
            final Integer skuSize = productAttributeValueList.size();
            List<StoreProductAttrValue> storeProductAttrValueList = storeProductAttrValueRepository
                    .findAllByProductIdAndDeleted(productId, 0);
            storeProductAttrValueList.forEach(storeProductAttrValue1 -> {
                storeProductAttrValue1.setDeleted(1);
                storeProductAttrValue1.setModifyTime(timestamp);
                storeProductAttrValueRepository.saveAndFlush(storeProductAttrValue1);
            });

            skuList.forEach(sku -> {
                String attrValue = "";
                if (skuSize == 1) {
                    attrValue = sku.getSp1();
                } else if (skuSize == 2) {
                    attrValue = sku.getSp1() + "," + sku.getSp2();
                } else {
                    attrValue = sku.getSp1() + "," + sku.getSp2() + "," + sku.getSp3();
                }
                StoreProductAttrValue storeProductAttrValue = new StoreProductAttrValue();
                storeProductAttrValue.setSuk(attrValue);
                storeProductAttrValue.setBarCode(sku.getBarCode());
                storeProductAttrValue.setCost(sku.getCost());
                storeProductAttrValue.setPrice(sku.getPrice());
                storeProductAttrValue.setImage(StringUtils.join(sku.getPic(), ","));
                storeProductAttrValue.setUnique(IdUtil.simpleUUID());
                storeProductAttrValue.setStock(sku.getStock());
                storeProductAttrValue.setSales(sku.getSales());
                storeProductAttrValue.setProductId(productId);
                storeProductAttrValue.setDeleted(0);
                storeProductAttrValue.setAddTime(timestamp);
                storeProductAttrValue.setModifyTime(timestamp);
                storeProductAttrValueRepository.save(storeProductAttrValue);
                index[0]++;
            });
        }

        if (productAttributeValueList != null && productAttributeValueList.size() > 0) {
            List<StoreProductAttr> storeProductAttrList = storeProductAttrRepository
                    .findAllByProductIdAndDeleted(productId, 0);
            storeProductAttrList.forEach(storeProductAttr -> {
                storeProductAttr.setDeleted(1);
                storeProductAttr.setModifyTime(timestamp);
                storeProductAttrRepository.save(storeProductAttr);
            });

            productAttributeValueList.forEach(productAttr -> {
                StoreProductAttr storeProductAttr = new StoreProductAttr();
                storeProductAttr.setCatalogAttrId(productAttr.getProductAttributeId());
                storeProductAttr.setAttrName(productAttr.getName());
                storeProductAttr.setAttrValues(productAttr.getValue());
                storeProductAttr.setProductId(productId);
                storeProductAttr.setType(productAttr.getType());
                storeProductAttr.setDeleted(0);
                storeProductAttr.setAddTime(timestamp);
                storeProductAttr.setModifyTime(timestamp);
                storeProductAttrRepository.save(storeProductAttr);
            });
        }
    }

    @Override
    public void saveProductRelationSubjects(Long productId, StoreProduct storeProduct) {
        List<SubjectProductRelationParam> subjectProductRelationParams = storeProduct.getSubjectProductRelationList();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long storeId = storeProduct.getStoreId();
        if (subjectProductRelationParams != null) {
            List<CmsSubjectProductRelation> cmsSubjectProductRelationList = cmsSubjectProductRelationRepository
                    .findByProductIdAndDeleted(productId, 0);
            cmsSubjectProductRelationList.forEach(cmsSubjectProductRelation -> {
                cmsSubjectProductRelation.setModifyTime(timestamp);
                cmsSubjectProductRelation.setDeleted(1);
                cmsSubjectProductRelationRepository.save(cmsSubjectProductRelation);
            });

            cmsSubjectProductRelationList = subjectProductRelationParams.stream().map(subjectProductRelationParam -> {
                CmsSubjectProductRelation cmsSubjectProductRelation = new CmsSubjectProductRelation();
                cmsSubjectProductRelation.setProductId(productId);
                cmsSubjectProductRelation.setStoreId(storeId);
                cmsSubjectProductRelation.setSubjectId(subjectProductRelationParam.getSubjectId());
                cmsSubjectProductRelation.setAddTime(timestamp);
                cmsSubjectProductRelation.setModifyTime(timestamp);
                cmsSubjectProductRelation.setDeleted(0);
                return cmsSubjectProductRelation;
            }).collect(Collectors.toList());

            cmsSubjectProductRelationRepository.saveAll(cmsSubjectProductRelationList);
        }
    }

    @Override
    public void saveProductPrefrenceAreas(Long productId, StoreProduct storeProduct) {
        List<PrefrenceAreaProductRelationParam> prefrenceAreaProductRelationParams = storeProduct
                .getPrefrenceAreaProductRelationList();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (prefrenceAreaProductRelationParams != null) {
            List<CmsPrefrenceAreaProductRelation> cmsPrefrenceAreaProductRelationList = cmsPrefrenceAreaProductRelationRepository
                    .findByProductIdAndDeleted(productId, 0);
            cmsPrefrenceAreaProductRelationList.forEach(cmsPrefrenceAreaProductRelation -> {
                cmsPrefrenceAreaProductRelation.setModifyTime(timestamp);
                cmsPrefrenceAreaProductRelation.setDeleted(1);
                cmsPrefrenceAreaProductRelationRepository.save(cmsPrefrenceAreaProductRelation);
            });

            cmsPrefrenceAreaProductRelationList = prefrenceAreaProductRelationParams.stream()
                    .map(prefrenceAreaProductRelationParam -> {
                        CmsPrefrenceAreaProductRelation cmsPrefrenceAreaProductRelation = new CmsPrefrenceAreaProductRelation();
                        cmsPrefrenceAreaProductRelation.setProductId(productId);
                        cmsPrefrenceAreaProductRelation
                                .setPrefrenceAreaId(prefrenceAreaProductRelationParam.getPrefrenceAreaId());
                        cmsPrefrenceAreaProductRelation.setAddTime(timestamp);
                        cmsPrefrenceAreaProductRelation.setModifyTime(timestamp);
                        cmsPrefrenceAreaProductRelation.setDeleted(0);
                        return cmsPrefrenceAreaProductRelation;
                    })
                    .collect(Collectors.toList());

            cmsPrefrenceAreaProductRelationRepository.saveAll(cmsPrefrenceAreaProductRelationList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreProduct resources) {
        Optional<StoreProduct> optionalStoreProduct = storeProductRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalStoreProduct, "StoreProduct", "id", resources.getId());
        StoreProduct storeProduct = optionalStoreProduct.get();
        storeProduct.copy(resources);
        storeProductRepository.save(storeProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        storeProductRepository.updateDel(1, id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recovery(Long id) {
        storeProductRepository.updateDel(0, id);
        storeProductRepository.updateOnsale(0, id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onSale(Long id, Integer status) {
        if (status == 1) {
            status = 0;
        } else {
            status = 1;
        }
        storeProductRepository.updateOnsale(status, id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ProductFormatDTO> isFormatAttr(Long id, String jsonStr) {
        if (ObjectUtil.isNull(id)) {
            throw new BadRequestException("产品不存在");
        }

        StoreProductDTO storeProductDTO = findById(id);

        DetailDTO detailDTO = attrFormat(jsonStr);

        //System.out.println("list:"+detailDTO.getRes());
        List<ProductFormatDTO> newList = new ArrayList<>();
        for (Map<String, Map<String, String>> map : detailDTO.getRes()) {
            ProductFormatDTO productFormatDTO = new ProductFormatDTO();

            productFormatDTO.setDetail(map.get("detail"));
            productFormatDTO.setCost(storeProductDTO.getCost().doubleValue());
            productFormatDTO.setPrice(storeProductDTO.getPrice().doubleValue());
            productFormatDTO.setSales(storeProductDTO.getSales());
            productFormatDTO.setPic(storeProductDTO.getImage());
            productFormatDTO.setCheck(false);
            newList.add(productFormatDTO);

        }

        return newList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createProductAttr(Long id, String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        //System.out.println(jsonObject);
        List<FromatDetailDTO> attrList = JSON.parseArray(jsonObject.get("items").toString(), FromatDetailDTO.class);
        List<ProductFormatDTO> valueList = JSON.parseArray(jsonObject.get("attrs").toString(), ProductFormatDTO.class);

        List<StoreProductAttr> attrGroup = new ArrayList<>();
        for (FromatDetailDTO fromatDetailDTO : attrList) {
            StoreProductAttr storeProductAttr = new StoreProductAttr();
            storeProductAttr.setProductId(id);
            storeProductAttr.setAttrName(fromatDetailDTO.getValue());
            storeProductAttr.setAttrValues(StrUtil.join(",", fromatDetailDTO.getDetail()));
            attrGroup.add(storeProductAttr);
        }

        List<StoreProductAttrValue> valueGroup = new ArrayList<>();
        for (ProductFormatDTO productFormatDTO : valueList) {
            StoreProductAttrValue storeProductAttrValue = new StoreProductAttrValue();
            storeProductAttrValue.setProductId(id);
            //productFormatDTO.getDetail().values().stream().collect(Collectors.toList());
            List<String> stringList = productFormatDTO.getDetail().values().stream().collect(Collectors.toList());
            Collections.sort(stringList);
            storeProductAttrValue.setSuk(StrUtil.join(",", stringList));
            storeProductAttrValue.setPrice(BigDecimal.valueOf(productFormatDTO.getPrice()));
            storeProductAttrValue.setCost(BigDecimal.valueOf(productFormatDTO.getCost()));
            storeProductAttrValue.setStock(productFormatDTO.getSales());
            storeProductAttrValue.setUnique(IdUtil.simpleUUID());
            storeProductAttrValue.setImage(productFormatDTO.getPic());

            valueGroup.add(storeProductAttrValue);
        }

        if (attrGroup.isEmpty() || valueGroup.isEmpty()) {
            throw new BadRequestException("请设置至少一个属性!");
        }

        //插入之前清空
        clearProductAttr(id, false);

        //保存属性
        storeProductAttrRepository.saveAll(attrGroup);

        //保存值
        storeProductAttrValueRepository.saveAll(valueGroup);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("attr", jsonObject.get("items"));
        map.put("value", jsonObject.get("attrs"));

        //保存结果
        setResult(map, id);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setResult(Map<String, Object> map, Long id) {
        StoreProductAttrResult storeProductAttrResult = new StoreProductAttrResult();
        storeProductAttrResult.setProductId(id);
        storeProductAttrResult.setResult(JSON.toJSONString(map));
        storeProductAttrResult.setChangeTime(OrderUtil.getSecondTimestampTwo());

        storeProductAttrResultRepository.deleteByProductId(id);

        storeProductAttrResultRepository.save(storeProductAttrResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearProductAttr(Long id, boolean isActice) {
        if (ObjectUtil.isNull(id)) {
            throw new BadRequestException("产品不存在");
        }

        storeProductAttrRepository.deleteByProductId(id);
        storeProductAttrValueRepository.deleteByProductId(id);

        if (isActice) {
            storeProductAttrResultRepository.deleteByProductId(id);
        }
    }

    @Override
    public String getStoreProductAttrResult(Long id) {
        StoreProductAttrResult storeProductAttrResult = storeProductAttrResultRepository.findByProductId(id);
        if (ObjectUtil.isNull(storeProductAttrResult)) {
            return "";
        }
        return storeProductAttrResult.getResult();
    }

    /**
     * 组合规则属性算法
     *
     * @param jsonStr
     * @return
     */
    public DetailDTO attrFormat(String jsonStr) {
        // List<Object> returnList = new ArrayList<>();

        JSONObject jsonObject = JSON.parseObject(jsonStr);
        List<FromatDetailDTO> fromatDetailDTOList = JSON.parseArray(jsonObject.get("items").toString(),
                FromatDetailDTO.class);
        List<String> data = new ArrayList<>();
        //List<Map<String,List<Map<String,String>>>> res =new ArrayList<>();
        List<Map<String, Map<String, String>>> res = new ArrayList<>();

        if (fromatDetailDTOList.size() > 1) {
            for (int i = 0; i < fromatDetailDTOList.size() - 1; i++) {
                if (i == 0) {
                    data = fromatDetailDTOList.get(i).getDetail();
                }
                List<String> tmp = new LinkedList<>();
                for (String v : data) {
                    for (String g : fromatDetailDTOList.get(i + 1).getDetail()) {
                        String rep2 = "";
                        if (i == 0) {
                            rep2 = fromatDetailDTOList.get(i).getValue() + "_" + v + "-"
                                    + fromatDetailDTOList.get(i + 1).getValue() + "_" + g;
                        } else {
                            rep2 = v + "-" + fromatDetailDTOList.get(i + 1).getValue() + "_" + g;
                        }

                        tmp.add(rep2);

                        if (i == fromatDetailDTOList.size() - 2) {
                            // Map<String,List<Map<String,String>>> rep4 = new LinkedHashMap<>();
                            Map<String, Map<String, String>> rep4 = new LinkedHashMap<>();
                            //List<Map<String,String>> listMap = new ArrayList<>();
                            //Map<String,String> map1 = new LinkedHashMap<>();
                            Map<String, String> reptemp = new LinkedHashMap<>();
                            for (String h : Arrays.asList(rep2.split("-"))) {
                                List<String> rep3 = Arrays.asList(h.split("_"));

                                if (rep3.size() > 1) {
                                    reptemp.put(rep3.get(0), rep3.get(1));
                                } else {
                                    reptemp.put(rep3.get(0), "");
                                }
                                //listMap.add(reptemp);

                            }
                            rep4.put("detail", reptemp);

                            //rep4.put("detail",listMap);

                            res.add(rep4);
                        }
                    }

                }

                //System.out.println("tmp:"+tmp);
                if (!tmp.isEmpty()) {
                    data = tmp;
                }
            }
        } else {
            List<String> dataArr = new ArrayList<>();

            for (FromatDetailDTO fromatDetailDTO : fromatDetailDTOList) {

                for (String str : fromatDetailDTO.getDetail()) {
                    Map<String, Map<String, String>> map2 = new LinkedHashMap<>();
                    //List<Map<String,String>> list1 = new ArrayList<>();
                    dataArr.add(fromatDetailDTO.getValue() + "_" + str);
                    Map<String, String> map1 = new LinkedHashMap<>();
                    map1.put(fromatDetailDTO.getValue(), str);
                    //list1.add(map1);
                    map2.put("detail", map1);
                    res.add(map2);
                }
            }

            String s = StrUtil.join("-", dataArr);
            data.add(s);
        }

        DetailDTO detailDTO = new DetailDTO();
        detailDTO.setData(data);
        detailDTO.setRes(res);

        return detailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onVerify(Long id, Integer status) {
        if (status == 1) {
            status = 0;
        } else {
            status = 1;
        }
        storeProductRepository.updateOnVerify(status, id);
    }

}
