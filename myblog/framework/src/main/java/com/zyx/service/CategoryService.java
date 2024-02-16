package com.zyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyx.domin.ResponseResult;
import com.zyx.domin.entity.Category;
import com.zyx.domin.vo.CategoryVo;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2024-01-30 14:57:25
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    ResponseResult getAllCategoryPage(Long pageNum, Long pageSize, String name, Integer status);

    ResponseResult addCategory(Category category);

    ResponseResult updateCategory(Category category);

    ResponseResult deleteCategory(Long id);
}

