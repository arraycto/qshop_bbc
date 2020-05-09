package co.lq.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 阿里云服务
 *
 * @author songbin
 * @since 2020年2月27日 上午10:32:00
 */
public interface AliyunService {

    /**
     * 上传文件
     *
     * @param file 上传的文件
     * @param path 上传相对路径
     */
    void uploadFile(MultipartFile file, String path);
}
