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

import co.lq.aop.log.Log;
import co.lq.service.AliyunService;
import co.lq.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 阿里云控制器
 *
 * @author songbin
 * @since 2020年2月27日 上午10:30:39
 */
@Slf4j
@RestController
@RequestMapping("/api/aliyunContent")
@Api(tags = "工具：阿里云存储管理")
public class AliyunController {

    @Value("${aliyun.url}")
    private String              aliyunUrl;

    @Value("${aliyun.path}")
    private String              aliyunPath;

    private final AliyunService aliyunService;

    public AliyunController(AliyunService aliyunService) {
        this.aliyunService = aliyunService;
    }

    @Log("上传文件")
    @ApiOperation("上传文件")
    @PostMapping
    public ResponseEntity<Object> upload(@RequestParam MultipartFile file) {
        String suffix = FileUtil.getExtensionName(file.getOriginalFilename());
        String type = FileUtil.getFileType(suffix);
        String originalFileName = file.getOriginalFilename();
        String url = aliyunPath + type + "/" + originalFileName.substring(0, originalFileName.indexOf(suffix) - 1) + "-"
                + UUID.randomUUID() + "." + suffix;
        aliyunService.uploadFile(file, url);
        Map<String, Object> map = new HashMap<>(3);
        map.put("id", "");
        map.put("errno", 0);
        map.put("data", new String[] { aliyunUrl + url });
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
