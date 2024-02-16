package com.zyx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyx.domin.entity.Role;
import com.zyx.mapper.RoleMapper;
import com.zyx.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2024-02-13 13:44:47
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRolePermissionByUserId(Long id) {
        //如果是管理员，返回admin
        if(id == 1L){
            List<String> roles = new ArrayList<>();
            roles.add("admin");
            return roles;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRolePermissionByUserId(id);
    }
}

