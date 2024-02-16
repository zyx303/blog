package com.zyx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyx.constants.SystemConstants;
import com.zyx.domin.entity.Menu;
import com.zyx.mapper.MenuMapper;
import com.zyx.service.MenuService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2024-02-13 13:41:25
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectMenuPermsByUserId(Long id) {
        //如果是管理员，返回所有权限
        if(id == 1L){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //根据用户id查询权限

        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        //如果是管理员，返回所有菜单
        if(userId == 1L){
            menus=  menuMapper.selectAllRouterMenu();
        }
        else {
            //否则根据用户id查询菜单
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        //构建Tree
        List<Menu> MenuTree = buildMenuTree(menus);
        return MenuTree;

    }

    private List<Menu> buildMenuTree(List<Menu> menus) {
        List<Menu> MenuTree = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getParentId() == 0) {
                MenuTree.add(menu);
            }
            for (Menu it : menus) {
                if (it.getParentId().equals(menu.getId())) {
                    if (menu.getChildren() == null) {
                        menu.setChildren(new ArrayList<>());
                    }
                    menu.getChildren().add(it);
                }
            }
        }
        return MenuTree;
    }
}

