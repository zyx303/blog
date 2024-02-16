package com.zyx.controller;

import com.zyx.domin.ResponseResult;
import com.zyx.domin.entity.LoginUser;
import com.zyx.domin.entity.Menu;
import com.zyx.domin.entity.User;
import com.zyx.domin.vo.AdminUserInfoVo;
import com.zyx.domin.vo.RoutersVo;
import com.zyx.domin.vo.UserInfoVo;
import com.zyx.enums.AppHttpCodeEnum;
import com.zyx.exception.SystemException;
import com.zyx.service.LoginService;
import com.zyx.service.MenuService;
import com.zyx.service.RoleService;
import com.zyx.utils.BeanCopyUtils;
import com.zyx.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        //没有用户名
        if(user.getUserName() == null){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @GetMapping("/getRouters")
    public ResponseResult getRouter() {
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectMenuTreeByUserId(userId);
        //封装返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    @GetMapping("/getInfo")
    public ResponseResult getInfo(){
        //获取当前用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限
        List<String> permissions = menuService.selectMenuPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roles = roleService.selectRolePermissionByUserId(loginUser.getUser().getId());
        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(permissions,roles,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
