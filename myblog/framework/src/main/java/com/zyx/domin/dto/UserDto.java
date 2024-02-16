package com.zyx.domin.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2024-02-05 15:06:33
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto  {
    //主键@TableId
    private Long id;

    //用户名
    private String userName;
    //昵称
    private String nickName;
    //邮箱
    private String email;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;



}

