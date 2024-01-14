package com.seed.controller;

import com.seed.domain.ResponseResult;
import com.seed.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2024/1/14 22:43
 */
@RestController
@RequestMapping
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestParam("img") MultipartFile file)  {
        try {
            return uploadService.upload(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传上传失败");
        }

    }
}
