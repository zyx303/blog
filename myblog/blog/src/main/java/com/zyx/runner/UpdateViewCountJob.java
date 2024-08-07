package com.zyx.runner;

import com.zyx.constants.SystemConstants;
import com.zyx.domin.entity.Article;
import com.zyx.service.ArticleService;
import com.zyx.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0 * * * ?")
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEW_COUNT);

        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .map(article -> {
                    //thumbnail设置回原来的值
                    article.setThumbnail(articleService.getById(article.getId()).getThumbnail());
                    return article;
                })
                .collect(Collectors.toList());
        //更新到数据库中
        //如果非空
        if(!articles.isEmpty())
            articleService.updateBatchById(articles);

    }
}
