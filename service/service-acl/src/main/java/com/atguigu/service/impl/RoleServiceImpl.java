package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.AdminRoleDao;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends
        BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AdminRoleDao adminRoleDao;

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    public Map<String, Object> findRoleByAdminId(Long adminId) {
        //查询所有的角色
        List<Role> allRolesList = roleDao.findAll();

        //拥有的角色id
        List<Long> existRoleIdList = adminRoleDao.findRoleIdByAdminId(adminId);

        //对角色进行分类
        List<Role> noAssginRoleList = new ArrayList<>();
        List<Role> assginRoleList = new ArrayList<>();
        for (Role role : allRolesList) {
            //已分配
            if (existRoleIdList.contains(role.getId())) {
                assginRoleList.add(role);
            } else {
                noAssginRoleList.add(role);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("noAssginRoleList", noAssginRoleList);
        roleMap.put("assginRoleList", assginRoleList);
        return roleMap;
    }

    @Override
    public void saveUserRoleRealtionShip(Long adminId, Long[] roleIds) {
        adminRoleDao.deleteByAdminId(adminId);

        for (Long roleId : roleIds) {
            if (StringUtils.isEmpty(roleId)) continue;
            AdminRole userRole = new AdminRole();
            userRole.setAdminId(adminId);
            userRole.setRoleId(roleId);
            adminRoleDao.insert(userRole);
        }
    }


    @Override
    protected BaseDao<Role> getEntityDao() {
        return roleDao;
    }


}
