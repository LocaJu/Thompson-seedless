package com.seed.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.domain.entity.ArticleTag;
import com.seed.service.ArticleTagService;
import com.seed.mapper.ArticleTagMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author 77286
* @description 针对表【tb_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2023-12-16 21:57:38
*/
@Slf4j
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
implements ArticleTagService{

}
