package com.seed.service;

import com.seed.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/30 17:42
 */
public interface UploadService {
    ResponseResult upload(MultipartFile file) throws IOException;
}
