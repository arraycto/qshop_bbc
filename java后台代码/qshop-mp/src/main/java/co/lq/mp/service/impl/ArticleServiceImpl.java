package co.lq.mp.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import co.lq.exception.ErrorRequestException;
import co.lq.mp.config.WxMpConfiguration;
import co.lq.mp.domain.Article;
import co.lq.mp.repository.ArticleRepository;
import co.lq.mp.service.ArticleService;
import co.lq.mp.service.dto.ArticleDTO;
import co.lq.mp.service.dto.ArticleQueryCriteria;
import co.lq.mp.service.mapper.ArticleMapper;
import co.lq.mp.utils.URLUtils;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassTagMessage;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;

/**
 * @author billy
 * @date 2019-10-07
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleMapper     articleMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    @Value("${file.path}")
    private String uploadDirStr;

    @Override
    public Map<String, Object> queryAll(ArticleQueryCriteria criteria, Pageable pageable) {
        Page<Article> page = articleRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(articleMapper::toDto));
    }

    @Override
    public List<ArticleDTO> queryAll(ArticleQueryCriteria criteria) {
        return articleMapper.toDto(articleRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public ArticleDTO findById(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        ValidationUtil.isNull(article, "Article", "id", id);
        return articleMapper.toDto(article.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleDTO create(Article resources) {
        //resources.setAddTime(String.valueOf(OrderUtil.getSecondTimestampTwo()));
        return articleMapper.toDto(articleRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Article resources) {
        Optional<Article> optionalArticle = articleRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalArticle, "Article", "id", resources.getId());
        Article article = optionalArticle.get();
        article.copy(resources);
        articleRepository.save(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public void uploadNews(ArticleDTO wxNewsArticleItem) throws Exception {

        WxMpService wxMpService = WxMpConfiguration.getWxMpService();

        WxMpMaterialNews wxMpMaterialNews = new WxMpMaterialNews();

        WxMpMaterialNews.WxMpMaterialNewsArticle article = new WxMpMaterialNews.WxMpMaterialNewsArticle();

        WxMpMaterialUploadResult wxMpMaterialUploadResult = uploadPhotoToWx(wxMpService,
                wxNewsArticleItem.getImageInput());
        wxNewsArticleItem.setThumbMediaId(wxMpMaterialUploadResult.getMediaId());

        article.setAuthor(wxNewsArticleItem.getAuthor());

        //处理content
        String content = processContent(wxMpService, wxNewsArticleItem.getContent());
        System.out.println(content);
        article.setContent(content);
        article.setContentSourceUrl(wxNewsArticleItem.getUrl());
        article.setDigest(wxNewsArticleItem.getSynopsis());
        article.setShowCoverPic(true);
        article.setThumbMediaId(wxNewsArticleItem.getThumbMediaId());
        article.setTitle(wxNewsArticleItem.getTitle());
        //TODO 暂时注释掉，测试号没有留言权限
        //article.setNeedOpenComment( wxNewsArticleItem );
        //article.setOnlyFansCanComment( wxNewsArticleItem );
        wxMpMaterialNews.addArticle(article);

        log.info("wxMpMaterialNews : {}", JSONUtil.toJsonStr(wxMpMaterialNews));

        WxMpMaterialUploadResult wxMpMaterialUploadResult1 = wxMpService.getMaterialService()
                .materialNewsUpload(wxMpMaterialNews);

        //推送开始
        WxMpMassTagMessage massMessage = new WxMpMassTagMessage();
        massMessage.setMsgType(WxConsts.MassMsgType.MPNEWS);
        massMessage.setMediaId(wxMpMaterialUploadResult1.getMediaId());
        massMessage.setSendAll(true);

        WxMpMassSendResult massResult = wxMpService.getMassMessageService().massGroupMessageSend(massMessage);
        if (!massResult.getErrorCode().equals("0")) {
            log.info("error:" + massResult.getErrorMsg());
            throw new ErrorRequestException("发送失败");
        }

        log.info("massResult : {}", JSONUtil.toJsonStr(massResult));

        log.info("wxMpMaterialUploadResult : {}", JSONUtil.toJsonStr(wxMpMaterialUploadResult1));
    }

    private WxMpMaterialUploadResult uploadPhotoToWx(WxMpService wxMpService, String picPath) throws WxErrorException {
        WxMpMaterial wxMpMaterial = new WxMpMaterial();

        String filename = String.valueOf(System.currentTimeMillis()) + ".png";
        String downloadPath = uploadDirStr + filename;
        long size = HttpUtil.downloadFile(picPath, FileUtil.file(downloadPath));
        picPath = downloadPath;
        File picFile = new File(picPath);
        wxMpMaterial.setFile(picFile);
        wxMpMaterial.setName(picFile.getName());
        log.info("picFile name : {}", picFile.getName());
        WxMpMaterialUploadResult wxMpMaterialUploadResult = wxMpService.getMaterialService()
                .materialFileUpload(WxConsts.MediaFileType.IMAGE, wxMpMaterial);
        log.info("wxMpMaterialUploadResult : {}", JSONUtil.toJsonStr(wxMpMaterialUploadResult));
        return wxMpMaterialUploadResult;
    }

    private String processContent(WxMpService wxMpService, String content) throws WxErrorException {
        if (StringUtils.isBlank(content)) {
            return content;
        }
        String imgReg = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
        List<String> imgList = ReUtil.findAllGroup1(imgReg, content);
        for (int j = 0; j < imgList.size(); j++) {
            String imgSrc = imgList.get(j);
            String filepath = URLUtils.getParam(imgSrc, "filepath");

            if (StringUtils.isBlank(filepath)) {//网络图片URL，需下载到本地
                String filename = String.valueOf(System.currentTimeMillis()) + ".png";
                String downloadPath = uploadDirStr + filename;
                long size = HttpUtil.downloadFile(imgSrc, FileUtil.file(downloadPath));
                filepath = downloadPath;
            }
            WxMediaImgUploadResult wxMediaImgUploadResult = wxMpService.getMaterialService()
                    .mediaImgUpload(new File(filepath));
            content = StringUtils.replace(content, imgList.get(j), wxMediaImgUploadResult.getUrl());
        }
        return content;
    }
}
