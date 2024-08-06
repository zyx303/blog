package com.zyx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyx.constants.SystemConstants;
import com.zyx.domin.ResponseResult;
import com.zyx.domin.dto.AddArticleDto;
import com.zyx.domin.entity.Article;
import com.zyx.domin.entity.Category;
import com.zyx.domin.vo.ArticleDetailVo;
import com.zyx.domin.vo.ArticleListVo;
import com.zyx.domin.vo.HotArticleVo;
import com.zyx.domin.vo.PageVo;
import com.zyx.mapper.ArticleMapper;
import com.zyx.service.ArticleService;
import com.zyx.service.CategoryService;
import com.zyx.utils.BeanCopyUtils;
import com.zyx.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ArticleServiceimpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    @Lazy
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //查询置顶的文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.eq(Article::getIsTop, SystemConstants.ARTICLE_IS_TOP);
//        queryWrapper.orderByDesc(Article::getViewCount);
//
//        Page<Article> page = new Page(1,10);
//        page(page,queryWrapper);

//        List<Article> articles = page.getRecords();

        //bean copy
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo =new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            articleVos.add(vo);
//        }
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(list(queryWrapper), HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Integer categoryId, String keyword) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //如果有keyword,模糊查询title以及summary
        lambdaQueryWrapper.like(Objects.nonNull(keyword),Article::getTitle,keyword)
                .or().like(Objects.nonNull(keyword),Article::getSummary,keyword);
        //如果有categoryId,查询时和传入相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态必须是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //置顶文章（istop=1）按照置顶降序，创建时间降序
        //非置顶文章按照创建时间降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop,Article::getCreateTime);
        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page, lambdaQueryWrapper);
        //查询categoryName
        List<Article> articles = page.getRecords();
        for(Article article:articles){
            Category category = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category.getName());
        }
        //封装ArticleListVo
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        //封装pageVo
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleById(Long id) {
        //查询文章
        Article article = getById(id);
        //封装vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(categoryId!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装相应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    @Transactional
    public ResponseResult add(AddArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult articleListByTitleAndSummary(Integer pageNum, Integer pageSize, String title, String summary) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //如果有title,模糊查询
        lambdaQueryWrapper.like(Objects.nonNull(title),Article::getTitle,title);
        //如果有summary,模糊查询
        lambdaQueryWrapper.like(Objects.nonNull(summary),Article::getSummary,summary);
        //置顶文章（istop=1）按照置顶降序，创建时间降序
        //非置顶文章按照创建时间降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop,Article::getCreateTime);
        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page, lambdaQueryWrapper);
        //封装ArticleListVo
        List<Article> articleList = BeanCopyUtils.copyBeanList(page.getRecords(), Article.class);
        //封装pageVo
        PageVo pageVo = new PageVo(articleList,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult updataViewwCount(Long id) {
        redisCache.incrementCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT,id.toString(),1);
        return ResponseResult.okResult();
    }
}
