package com.seed.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/30 17:32
 */
public class PathUtils {

    /**
     * 生成文件的地址
     * @param localFilePath 本地临时文件路径
     * @param extension 文件扩展名
     * @return
     */
    public static String generateFilePath(String localFilePath,String extension){
        //1、获取文件默认存储目录路径 年/月/日
        String defaultFolderPath = getDefaultFolderPath();
        //2、获取文件的md5
        String fileMd5 = getFileMd5(new File(localFilePath));
        //得到如下格式的路径
        //2023/12/30/f/f9308f21182589fd4f11b8cba135bab1/f9308f21182589fd4f11b8cba135bab1.vdb
        String filePath = getFilePathByMd5(fileMd5, extension);
        return defaultFolderPath+filePath;
    }



    //获取文件默认存储目录路径 年/月/日
    private static String getDefaultFolderPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String folder = sdf.format(new Date()).replace("-", "/")+"/";
        return folder;
    }
    //获取文件的md5
    private static String getFileMd5(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            String fileMd5 = DigestUtils.md5Hex(fileInputStream);
            return fileMd5;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getFilePathByMd5(String fileMd5,String fileExt){
        return   fileMd5.substring(0,1) + "/" + fileMd5 + "/" +fileMd5 +fileExt;
    }
}
