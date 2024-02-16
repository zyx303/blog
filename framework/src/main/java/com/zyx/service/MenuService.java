package com.zyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyx.domin.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2024-02-13 13:41:27
 */
public interface MenuService extends IService<Menu> {

    List<String> selectMenuPermsByUserId(Long id);

    List<Menu> selectMenuTreeByUserId(Long userId);
}

