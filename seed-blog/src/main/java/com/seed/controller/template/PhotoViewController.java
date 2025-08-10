package com.seed.controller.template;

import jakarta.annotation.security.PermitAll;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

/**
 * @author 77286
 * @version 1.0
 * @description: 静态网页
 * @date 2025/8/10 17:40
 */
@Controller
public class PhotoViewController {

    @GetMapping("/PhotoView")
    public String photoView(
            @RequestParam(name = "type", required = false, defaultValue = "") String type,
            @RequestParam(name = "keyvalue", required = false, defaultValue = "") String keyvalue,
            Model model) {

        // 模拟3张图片URL（实际项目中可以从数据库或文件系统获取）
        List<String> imageUrls = Arrays.asList(
                "https://cdn-data-platform.pg.com.cn/product-image/Product-Image-Details/efd95d403e80411a829bac222352795c.jpg",
                "https://cdn-data-platform.pg.com.cn/product-image/Product-Image-Details/958f3bf1dad141a1a001db3d7b1eec3f.jpg",
                "https://cdn-data-platform.pg.com.cn/product-image/Product-Image-Details/9e5a2a9acc5d4629888f441689735bf0.jpg"
        );

        // 将数据传递给视图
        model.addAttribute("type", type);
        model.addAttribute("keyvalue", keyvalue);
        model.addAttribute("imageUrls", imageUrls);

        return "photo-view"; // 对应templates/photo-view.html
    }
}
