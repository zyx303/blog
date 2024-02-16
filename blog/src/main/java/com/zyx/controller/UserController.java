package com.zyx.controller;

import com.zyx.domin.ResponseResult;
import com.zyx.domin.entity.User;
import com.zyx.domin.entity.UserDto;
import com.zyx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody UserDto user){
        return userService.updateUserInfo(user);
    }
}
