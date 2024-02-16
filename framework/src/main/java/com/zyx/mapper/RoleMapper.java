package com.zyx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyx.domin.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2024-02-13 13:44:49
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRolePermissionByUserId(Long id);
}

