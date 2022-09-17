package com.atguigu.dao;


import com.atguigu.base.BaseDao;
import com.atguigu.entity.Admin;

import java.util.List;

public interface AdminDao extends BaseDao<Admin> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    List<Admin> findAll();

    Admin getByUsername(String username);
}