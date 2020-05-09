package co.lq.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.util.StrUtil;
import co.lq.domain.QiniuContent;
import co.lq.service.AliyunService;
import co.lq.service.LocalStorageService;
import co.lq.service.QiNiuService;
import co.lq.service.dto.LocalStorageDTO;
import co.lq.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-01-09
 */
@Api(tags = "上传统一管理")
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Value("${file.localUrl}")
    private String                    localUrl;

    @Value("${aliyun.url}")
    private String                    aliyunUrl;

    @Value("${aliyun.path}")
    private String                    aliyunPath;

    private final LocalStorageService localStorageService;

    private final QiNiuService        qiNiuService;

    private final AliyunService       aliyunService;

    public UploadController(LocalStorageService localStorageService, QiNiuService qiNiuService,
                            AliyunService aliyunService) {
        this.localStorageService = localStorageService;
        this.qiNiuService = qiNiuService;
        this.aliyunService = aliyunService;
    }

    @ApiOperation("上传文件")
    @PostMapping
    public ResponseEntity<Object> create(@RequestParam(defaultValue = "") String name,
                                         @RequestParam("file") MultipartFile file) {
        String url = "";
        if (StrUtil.isNotEmpty(localUrl)) {
            //存在走本地
            LocalStorageDTO localStorageDTO = localStorageService.create(name, file);
            url = localUrl + "/file/" + localStorageDTO.getType() + "/" + localStorageDTO.getRealName();
        } else if (StrUtil.isNotBlank(aliyunUrl)) {
            //走阿里云
            String suffix = FileUtil.getExtensionName(file.getOriginalFilename());
            String type = FileUtil.getFileType(suffix);
            String originalFileName = file.getOriginalFilename();
            url = aliyunPath + type + "/" + originalFileName.substring(0, originalFileName.indexOf(suffix) - 1) + "-"
                    + UUID.randomUUID() + "." + suffix;
            aliyunService.uploadFile(file, url);
            url = aliyunUrl + url;
        } else {
            //走七牛云
            QiniuContent qiniuContent = qiNiuService.upload(file, qiNiuService.find());
            url = qiniuContent.getUrl();
        }

        Map<String, Object> map = new HashMap<>(2);
        map.put("errno", 0);
        map.put("link", url);
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

}
