package com.seed.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/30 17:29
 */
@Slf4j
@Component
public class OssUtils {
    @Value("${qiniu.oss.accessKey}")
    private  String accessKey;
    @Value("${qiniu.oss.secretKey}")
    private  String secretKey;
    @Value("${qiniu.oss.bucket}")
    private  String bucket;


    public  void UploadFile(MultipartFile imgFile,String tempAbsolutePath, String filePathName) throws IOException {

        //构造一个带指定Region对象的配置类
        Configuration cfg = new Configuration(Region.huadongZheJiang2());
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        FileInputStream fileInputStream = new FileInputStream(new File(tempAbsolutePath));

        try{
            Response response = uploadManager.put(fileInputStream,filePathName,upToken,null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        }catch (QiniuException ex) {
            ex.printStackTrace();
            if (ex.response != null) {
                System.err.println(ex.response);
                try {
                    String body = ex.response.toString();
                    System.err.println(body);
                } catch (Exception ignored) {
                    log.error(ignored.getMessage());
                }
            }
        }
    }
}
