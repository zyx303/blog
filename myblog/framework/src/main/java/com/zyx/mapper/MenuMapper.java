package com.zyx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyx.domin.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2024-02-13 13:41:27
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectMenuTreeByUserId(Long userId);
}

