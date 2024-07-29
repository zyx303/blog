package com.zyx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.zyx.domin.ResponseResult;
import com.zyx.domin.dto.UserDto;
import com.zyx.domin.dto.UserLoginDto;
import com.zyx.domin.entity.User;
import com.zyx.domin.vo.UserInfoVo;
import com.zyx.enums.AppHttpCodeEnum;
import com.zyx.mapper.UserMapper;
import com.zyx.service.UserService;
import com.zyx.utils.BeanCopyUtils;
import com.zyx.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    //注入密码加密类
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult register(UserLoginDto userLoginDto) {
        //非空判断
        if(StringUtils.isNullOrEmpty(userLoginDto.getUserName()))
        {
            return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(StringUtils.isNullOrEmpty(userLoginDto.getPassword()))
        {
            return ResponseResult.errorResult(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(StringUtils.isNullOrEmpty(userLoginDto.getEmail()))
        {
            return ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(StringUtils.isNullOrEmpty(userLoginDto.getNickName()))
        {
            return ResponseResult.errorResult(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //加密
        String password = passwordEncoder.encode(userLoginDto.getPassword());
        //设置密码
        userLoginDto.setPassword(password);
        //保存用户信息
        User user = BeanCopyUtils.copyBean(userLoginDto, User.class);
        save(user);


        return ResponseResult.okResult();
    }
}
