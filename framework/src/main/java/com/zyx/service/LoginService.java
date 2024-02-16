package com.zyx.service;

import com.zyx.domin.ResponseResult;
import com.zyx.domin.entity.User;

public interface LoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
