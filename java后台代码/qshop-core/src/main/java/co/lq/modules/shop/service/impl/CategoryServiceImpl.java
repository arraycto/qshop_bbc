package co.lq.modules.shop.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.hutool.core.util.ObjectUtil;
import co.lq.exception.BadRequestException;
import co.lq.modules.shop.domain.Category;
import co.lq.modules.shop.repository.CategoryRepository;
import co.lq.modules.shop.repository.StoreProductRepository;
import co.lq.modules.shop.service.CategoryService;
import co.lq.modules.shop.service.dto.CategoryDTO;
import co.lq.modules.shop.service.dto.CategoryQueryCriteria;
import co.lq.modules.shop.service.mapper.CategoryMapper;
import co.lq.utils.FileUtil;
import co.lq.utils.OrderUtil;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-10-03
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository     categoryRepository;
    private final StoreProductRepository storeProductRepository;

    private final CategoryMapper         categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, StoreProductRepository storeProductRepository,
                               CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.storeProductRepository = storeProductRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void download(List<CategoryDTO> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CategoryDTO storeCategoryDTO : queryAll) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("分类名称", storeCategoryDTO.getCateName());
            map.put("分类状态", storeCategoryDTO.getIsShow() == 1 ? "启用" : "停用");
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public Map<String, Object> queryAll(CategoryQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<Category> page = categoryRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(categoryMapper::toDto));
    }

    @Override
    public List<CategoryDTO> queryAll(CategoryQueryCriteria criteria) {
        criteria.setDeleted(0);
        return categoryMapper.toDto(categoryRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public CategoryDTO findById(Long id) {
        Optional<Category> yxStoreCategory = categoryRepository.findById(id);
        ValidationUtil.isNull(yxStoreCategory, "Category", "id", id);
        return categoryMapper.toDto(yxStoreCategory.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO create(Category resources) {
        if (ObjectUtil.isNull(resources.getPid()))
            resources.setPid(0L);
        if (ObjectUtil.isNull(resources.getSort()))
            resources.setSort(1);
        if (ObjectUtil.isNull(resources.getDeleted())) {
            resources.setDeleted(0);
        }
        if (ObjectUtil.isNull(resources.getAddTime())) {
            resources.setAddTime(OrderUtil.getSecondTimestampTwo());
        }
        if (ObjectUtil.isNull(resources.getModifyTime())) {
            resources.setModifyTime(OrderUtil.getSecondTimestampTwo());
        }
        return categoryMapper.toDto(categoryRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Category resources) {
        Optional<Category> optionalYxStoreCategory = categoryRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalYxStoreCategory, "Category", "id", resources.getId());
        Category category = optionalYxStoreCategory.get();
        category.copy(resources);
        category.setModifyTime(OrderUtil.getSecondTimestampTwo());
        categoryRepository.save(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Category category = categoryRepository.findByPid(id);
        if (category != null)
            throw new BadRequestException("请先删除子类");

        //        List<StoreProduct> storeProduct = storeProductRepository.findByCategory_Id(String.valueOf(id));
        //        if (!storeProduct.isEmpty())
        //            throw new BadRequestException("此分类下有商品,不能删除");

        category = categoryRepository.findById(id).orElseGet(Category::new);
        category.setModifyTime(OrderUtil.getSecondTimestampTwo());
        category.setDeleted(1);
        categoryRepository.save(category);
    }

    @Override
    public Object buildTree(List<CategoryDTO> categoryDTOS) {
        Set<CategoryDTO> trees = new LinkedHashSet<>();
        Set<CategoryDTO> cates = new LinkedHashSet<>();
        List<String> deptNames = categoryDTOS.stream().map(CategoryDTO::getCateName).collect(Collectors.toList());

        Boolean isChild;
        for (CategoryDTO deptDTO : categoryDTOS) {
            isChild = false;
            if ("0".equals(deptDTO.getPid().toString())) {
                trees.add(deptDTO);
            }
            for (CategoryDTO it : categoryDTOS) {
                if (it.getPid().equals(deptDTO.getId())) {
                    isChild = true;
                    if (deptDTO.getChildren() == null) {
                        deptDTO.setChildren(new ArrayList<CategoryDTO>());
                    }
                    deptDTO.getChildren().add(it);
                }
            }
            if (isChild)
                cates.add(deptDTO);
            else if (!deptNames.contains(categoryRepository.findNameById(deptDTO.getPid())))
                cates.add(deptDTO);
        }

        if (CollectionUtils.isEmpty(trees)) {
            trees = cates;
        }

        Integer totalElements = categoryDTOS.size();

        Map map = new HashMap();
        map.put("totalElements", totalElements);
        map.put("content", CollectionUtils.isEmpty(trees) ? categoryDTOS : trees);
        return map;
        //return null;
    }
}
