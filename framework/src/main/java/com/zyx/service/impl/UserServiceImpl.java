package com.zyx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyx.domin.ResponseResult;
import com.zyx.domin.entity.User;
import com.zyx.domin.entity.UserDto;
import com.zyx.domin.vo.UserInfoVo;
import com.zyx.mapper.UserMapper;
import com.zyx.service.UserService;
import com.zyx.utils.BeanCopyUtils;
import com.zyx.utils.SecurityUtils;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo返回
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(UserDto userDto) {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //设置用户id
        userDto.setId(userId);
        //更新用户信息
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        updateById(user);
        return ResponseResult.okResult();
    }
}
