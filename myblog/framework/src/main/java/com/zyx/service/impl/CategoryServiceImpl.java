package com.zyx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.UpdateById;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyx.constants.SystemConstants;
import com.zyx.domin.ResponseResult;
import com.zyx.domin.entity.Article;
import com.zyx.domin.entity.Category;
import com.zyx.domin.vo.CategoryVo;
import com.zyx.domin.vo.PageVo;
import com.zyx.mapper.CategoryMapper;
import com.zyx.service.ArticleService;
import com.zyx.service.CategoryService;
import com.zyx.utils.BeanCopyUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2024-01-30 14:57:23
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
//        //查询文章表 状态为已发布 未被删除
//        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
//        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
//        List<Article> articleList = articleService.list(articleWrapper);
//        //获取文章的分类id,去重
//        Set<Long> categoryIds = articleList.stream()
//                .map(article -> article.getCategoryId())
//                .collect(Collectors.toSet());
//        //查询分类表
//        List<Category> categories = listByIds(categoryIds);
//        categories = categories.stream()
//                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
//                .collect(Collectors.toList());
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> categories = list(wrapper);
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public ResponseResult getAllCategoryPage(Long pageNum, Long pageSize, String name, Integer status) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !"".equals(name)){
            queryWrapper.like(Category::getName,name);
        }
        if (status != null){
            queryWrapper.eq(Category::getStatus,status);
        }
        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        //封装pageVo
        return ResponseResult.okResult(new PageVo(page.getRecords(),page.getTotal()));
    }
    //新增
    @Override
    public ResponseResult addCategory(Category category) {
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateCategory(Category category) {
        update(category,new LambdaQueryWrapper<Category>().eq(Category::getId,category.getId()));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

}

