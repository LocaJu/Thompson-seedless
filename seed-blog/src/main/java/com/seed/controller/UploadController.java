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
 * @date 2023/12/30 17:40
 */
@RestController
public class UploadController {

    @Autowired
    UploadService uploadService;


    @RequestMapping("/upload")
    public ResponseResult upload(@RequestParam("img") MultipartFile file) throws IOException {

        return uploadService.upload(file);
    }
}
