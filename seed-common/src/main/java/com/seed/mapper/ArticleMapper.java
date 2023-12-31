package com.seed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seed.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 77286
* @description 针对表【tb_article(文章表)】的数据库操作Mapper
* @createDate 2023-12-10 16:18:36
* @Entity seed.domain.Article
*/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {


}
