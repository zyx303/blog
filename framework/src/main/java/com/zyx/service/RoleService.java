package com.zyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyx.domin.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2024-02-13 13:44:48
 */
public interface RoleService extends IService<Role> {

    List<String> selectRolePermissionByUserId(Long id);
}

