package co.lq.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.util.ObjectUtil;
import co.lq.domain.LocalStorage;
import co.lq.exception.BadRequestException;
import co.lq.repository.LocalStorageRepository;
import co.lq.service.LocalStorageService;
import co.lq.service.dto.LocalStorageDTO;
import co.lq.service.dto.LocalStorageQueryCriteria;
import co.lq.service.mapper.LocalStorageMapper;
import co.lq.utils.FileUtil;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.SecurityUtils;
import co.lq.utils.StringUtils;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-09-05
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LocalStorageServiceImpl implements LocalStorageService {

    private final LocalStorageRepository localStorageRepository;

    private final LocalStorageMapper     localStorageMapper;

    @Value("${file.path}")
    private String                       path;

    @Value("${file.maxSize}")
    private long                         maxSize;

    public LocalStorageServiceImpl(LocalStorageRepository localStorageRepository,
                                   LocalStorageMapper localStorageMapper) {
        this.localStorageRepository = localStorageRepository;
        this.localStorageMapper = localStorageMapper;
    }

    @Override
    public Object queryAll(LocalStorageQueryCriteria criteria, Pageable pageable) {
        Page<LocalStorage> page = localStorageRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(localStorageMapper::toDto));
    }

    @Override
    public List<LocalStorageDTO> queryAll(LocalStorageQueryCriteria criteria) {
        return localStorageMapper.toDto(localStorageRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public LocalStorageDTO findById(Long id) {
        LocalStorage localStorage = localStorageRepository.findById(id).orElseGet(LocalStorage::new);
        ValidationUtil.isNull(localStorage.getId(), "LocalStorage", "id", id);
        return localStorageMapper.toDto(localStorage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LocalStorageDTO create(String name, MultipartFile multipartFile) {
        FileUtil.checkSize(maxSize, multipartFile.getSize());
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtil.getFileType(suffix);
        File file = FileUtil.upload(multipartFile, path + type + File.separator);
        if (ObjectUtil.isNull(file)) {
            throw new BadRequestException("上传失败");
        }
        try {
            name = StringUtils.isBlank(name) ? FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename()) : name;
            LocalStorage localStorage = new LocalStorage(file.getName(), name, suffix, file.getPath(), type,
                    FileUtil.getSize(multipartFile.getSize()), SecurityUtils.getUsername());
            return localStorageMapper.toDto(localStorageRepository.save(localStorage));
        } catch (Exception e) {
            FileUtil.del(file);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LocalStorage resources) {
        LocalStorage localStorage = localStorageRepository.findById(resources.getId()).orElseGet(LocalStorage::new);
        ValidationUtil.isNull(localStorage.getId(), "LocalStorage", "id", resources.getId());
        localStorage.copy(resources);
        localStorageRepository.save(localStorage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            LocalStorage storage = localStorageRepository.findById(id).orElseGet(LocalStorage::new);
            FileUtil.del(storage.getPath());
            localStorageRepository.delete(storage);
        }
    }

    @Override
    public void download(List<LocalStorageDTO> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (LocalStorageDTO localStorageDTO : queryAll) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("文件名", localStorageDTO.getRealName());
            map.put("备注名", localStorageDTO.getName());
            map.put("文件类型", localStorageDTO.getType());
            map.put("文件大小", localStorageDTO.getSize());
            map.put("操作人", localStorageDTO.getOperate());
            map.put("创建日期", localStorageDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
