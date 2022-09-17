package com.atguigu.service;


import com.atguigu.base.BaseService;
import com.atguigu.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role> {

    List<Role> findAll();
    /**
     * 根据用户获取角色数据
     * @param adminId
     * @return
     */
    Map<String, Object> findRoleByAdminId(Long adminId);

    /**
     * 分配角色
     * @param adminId
     * @param roleIds
     */
    void saveUserRoleRealtionShip(Long adminId, Long[] roleIds);

}
