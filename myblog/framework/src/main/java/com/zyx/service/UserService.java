package com.zyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyx.domin.ResponseResult;
import com.zyx.domin.dto.UserDto;
import com.zyx.domin.dto.UserLoginDto;
import com.zyx.domin.entity.User;


public interface UserService extends IService<User> {
    ResponseResult userInfo();

    ResponseResult updateUserInfo(UserDto userDto);

    ResponseResult register(UserLoginDto userLoginDto);
}
