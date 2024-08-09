package com.zyx.controller;

import com.zyx.domin.ResponseResult;
import com.zyx.domin.entity.Article;
import com.zyx.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

//    @GetMapping("/list")
//    public List<Article> test(){
//        return articleService.list();
//    }

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        //查询热门文章
        ResponseResult result =  articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId,String keyword){
        //查询文章
        ResponseResult result =  articleService.articleList(pageNum,pageSize,categoryId,keyword);
        return result;
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleById(@PathVariable("id") Long id) {
        //查询文章
        return articleService.getArticleById(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updataViewwCount(@PathVariable("id") Long id){
        //更新浏览量
        return articleService.updataViewwCount(id);
    }

}
