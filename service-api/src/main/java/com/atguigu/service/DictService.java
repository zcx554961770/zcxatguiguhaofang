package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService extends BaseService<Dict> {

    List<Map<String,Object>> findZnodes(Long id);
    List<Dict> findListByDictCode(String dictCode);
    List<Dict> findListByParentId(Long parentId) ;

    String getNameById(Long houseTypeId);
}
