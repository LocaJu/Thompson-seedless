package com.seed.service.impl;

import com.seed.domain.ResponseResult;
import com.seed.service.UploadService;
import com.seed.utils.OssUtils;
import com.seed.utils.PathUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/30 17:42
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    OssUtils ossUtils;
    @Value("${qiniu.CDN}")
    String CDN;

    @Override
    public ResponseResult upload(MultipartFile file) throws IOException {
        //上传文件名称
        String originalFilename = file.getOriginalFilename();
        //截取扩展名
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //创建临时文件
        File tempFile = File.createTempFile("oss", "temp");
        //上传的文件拷贝到临时文件
        file.transferTo(tempFile);
        //文件路径
        String absolutePath = tempFile.getAbsolutePath();
        //生成文件路径
        String filePathName = PathUtils.generateFilePath(absolutePath, extension);

        ossUtils.UploadFile(file,absolutePath,filePathName);

        //文件浏览全路径
        String url=CDN+"/"+filePathName;
        return ResponseResult.okResult(url);
    }
}
