package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.CommunityDao;
import com.atguigu.entity.Community;
import com.atguigu.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {


    @Autowired
    public CommunityDao communityDao;


    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }

    @Override
    protected BaseDao<Community> getEntityDao() {
        return communityDao;
    }
}
