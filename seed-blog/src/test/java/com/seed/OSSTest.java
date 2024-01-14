package com.seed;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.seed.utils.PathUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/30 15:31
 */

@SpringBootTest
@Component
@ConfigurationProperties(prefix = "oss")
public class OSSTest {
    String accessKey;
    String secretKey;
    String bucket;

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    @Test
    void testOss(){
        //构造一个带指定Region对象的配置类
        Configuration cfg = new Configuration(Region.huadongZheJiang2());
        UploadManager uploadManager = new UploadManager(cfg);
//        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释



        //...生成上传凭证，然后准备上传

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "a.txt";
        try {
            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(byteInputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                String url = response.url();
                String address = response.address;
                System.out.println(url);

                System.out.println(address);
            } catch (QiniuException ex) {
                ex.printStackTrace();
                if (ex.response != null) {
                    System.err.println(ex.response);
                    try {
                        String body = ex.response.toString();
                        System.err.println(body);
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        }

    }

    @Test
    void filePathTest(){
        String s = PathUtils.generateFilePath("D:\\000.vdb", ".vdb");
        System.out.println(s);

    }

    @Test
    void url(){
        String avatarUrl ="http://s66bj54ml.bkt.clouddn.com/2023/12/30/2/2eacc66309ade14644a6b9b4f9bbf0ee/2eacc66309ade14644a6b9b4f9bbf0ee.csv";
//        String substring = avatarUrl.substring(avatarUrl.indexOf("/", avatarUrl.indexOf("/", avatarUrl.indexOf("/") + 1) + 1) + 1);
//
//        int i = avatarUrl.indexOf(".com/");
//        String substring1 = avatarUrl.substring(i+".com/".length());
//
//        System.out.println(substring+"---"+substring1);

        String replace = avatarUrl.replace("http://s66bj54ml.bkt.clouddn.com", "");
        System.out.println(replace);
    }
}
