package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseService;
import com.atguigu.entity.Dict;
import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DictDao extends BaseDao<Dict> {
    int deleteByPrimaryKey(Long id);


    int insertSelective(Dict record);


    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);

    List<Dict> findListByParentId(Long parentId);
    Integer countIsParent(Long id);
    Dict getByDictCode(String dictCode);
    String getNameById(Long id);
}