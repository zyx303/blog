package com.zyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyx.domin.ResponseResult;
import com.zyx.domin.entity.User;
import com.zyx.domin.entity.UserDto;


public interface UserService extends IService<User> {
    ResponseResult userInfo();

    ResponseResult updateUserInfo(UserDto user);
}
