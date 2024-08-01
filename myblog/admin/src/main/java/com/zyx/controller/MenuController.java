package com.zyx.controller;

import com.zyx.domin.ResponseResult;
import com.zyx.domin.entity.Menu;
import com.zyx.enums.AppHttpCodeEnum;
import com.zyx.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping("/list")
    public ResponseResult list() {
        return ResponseResult.okResult(menuService.list());
    }

    @PostMapping
    public ResponseResult add(@RequestBody Menu menu) {
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable("id") Long id) {
        return ResponseResult.okResult(menuService.getById(id));
    }

    @PutMapping()
    public ResponseResult update(@RequestBody Menu menu) {
        if(menu.getId().equals(menu.getParentId()))
        {
            return ResponseResult.errorResult(AppHttpCodeEnum.MENU_ERROR);
        }
        menuService.updateById(menu);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") Long id) {
        //得到menulist
        List<Menu> menuList = menuService.list();
        //如果有子菜单
        for(Menu menu:menuList)
        {
            if(menu.getParentId().equals(id))
            {
                return ResponseResult.errorResult(AppHttpCodeEnum.MENU_DELETE_ERROR);
            }
        }
        menuService.removeById(id);
        return ResponseResult.okResult();
    }
}
