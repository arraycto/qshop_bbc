package co.lq.service.impl;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import co.lq.exception.BadRequestException;
import co.lq.service.AliyunService;

/**
 * 阿里云服务实现
 *
 * @author songbin
 * @since 2020年2月27日 上午10:39:45
 */
@Service
public class AliyunServiceImpl implements AliyunService {

    @Value("${aliyun.endpoint}")
    private String endpoint;

    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.bucketName}")
    private String bucketName;

    @Override
    public void uploadFile(MultipartFile file, String path) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 上传网络流。
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(bucketName, path, inputStream);
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
