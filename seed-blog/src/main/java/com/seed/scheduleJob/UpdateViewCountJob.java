package com.seed.scheduleJob;

import com.seed.domain.entity.Article;
import com.seed.service.ArticleService;
import com.seed.utils.RedisCache;
import com.seed.utils.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2024/1/1 14:08
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    RedisCache redisCache;

    @Autowired
    ArticleService articleService;

    @Scheduled(cron = "* 0/50 * * * ?")
    public void updateViewCount() {
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(RedisConstants.ARTICLE_VIEW_COUNT);
        List<Article> articleList = viewCountMap.entrySet().stream()
                .map(entry ->
                        new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库
        articleService.updateBatchById(articleList);
    }
}