package com.zyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyx.domin.ResponseResult;
import com.zyx.domin.dto.AddArticleDto;
import com.zyx.domin.entity.Article;
import org.springframework.stereotype.Service;

public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Integer categoryId, String keyword);

    ResponseResult getArticleById(Long id);

    ResponseResult add(AddArticleDto article);

    ResponseResult articleListByTitleAndSummary(Integer pageNum, Integer pageSize, String title, String summary);
}
