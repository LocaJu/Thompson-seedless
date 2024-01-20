package com.seed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seed.domain.entity.Article;
import com.seed.mapper.ArticleMapper;
import com.seed.service.ArticleService;
import com.seed.utils.RedisCache;
import com.seed.utils.RedisConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class RedisTest {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    @Resource
    private ArticleMapper articleMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void updateViewCount() {
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(RedisConstants.ARTICLE_VIEW_COUNT);
        List<Article> articleList = viewCountMap.entrySet().stream()
                .map(entry ->
                        new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库
        String sql = "update tb_article set view_count = ?,update_time = now() where id = ?";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,articleList.get(i).getViewCount().toString());
                preparedStatement.setLong(2,articleList.get(i).getId());
            }
            @Override
            public int getBatchSize() {
                return articleList.size();
            }
        });
    }
}
