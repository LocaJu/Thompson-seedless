package com.seed.runner;

import com.seed.domain.entity.Article;
import com.seed.mapper.ArticleMapper;
import com.seed.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.seed.utils.RedisConstants.ARTICLE_VIEW_COUNT;

/**
 * @author 77286
 * @version 1.0
 * @description: 热更新文章浏览量到redis
 * @date 2024/1/1 13:16
 */
@Component
public class ArticleViewCountRunner implements CommandLineRunner {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    RedisCache redisCache;


    @Override
    public void run(String... args) throws Exception {
        List<Article> articleList = articleMapper.selectList(null);
        //查询博客信息转为map(id-viewCount)
        Map<String, Integer> articleViewCountMap = articleList.stream()
                .collect(Collectors.toMap(
                        article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()
                ));
        //存入redis
        redisCache.setCacheMap(ARTICLE_VIEW_COUNT,articleViewCountMap);
    }
}
