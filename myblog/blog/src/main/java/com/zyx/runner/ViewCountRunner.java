package com.zyx.runner;

import com.zyx.constants.SystemConstants;
import com.zyx.domin.entity.Article;
import com.zyx.mapper.ArticleMapper;
import com.zyx.service.ArticleService;
import com.zyx.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.activation.CommandObject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //把博客信息存储到redis中，便于更新
        List<Article> articles = articleMapper.selectList(null);
        Map<String,Integer> viewCountMap = articles.stream().collect(Collectors.toMap(
                article -> article.getId().toString(),
                article -> {
                    return article.getViewCount().intValue();
                }
        ));
        redisCache.setCacheMap(SystemConstants.ARTICLE_VIEW_COUNT,viewCountMap);
    }
}
