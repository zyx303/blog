package com.zyx.controller;

import com.zyx.domin.ResponseResult;
import com.zyx.domin.dto.AddArticleDto;
import com.zyx.domin.entity.Article;
import com.zyx.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String title, String summary){
        return articleService.articleListByTitleAndSummary(pageNum,pageSize,title,summary);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleById(@PathVariable("id") Long id) {
        return articleService.getArticleById(id);
    }

    @PutMapping
    public ResponseResult update(@RequestBody Article article){
        return ResponseResult.okResult(articleService.updateById(article));
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") Long id){
        return ResponseResult.okResult(articleService.removeById(id));
    }
}
