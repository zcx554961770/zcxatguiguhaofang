package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = DictService.class )
@Transactional
public class DictSeviceImpl extends BaseServiceImpl<Dict> implements DictService {

    @Autowired
    public DictDao dictDao;
    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        // 返回数据[{ id:2, isParent:true, name:"随意勾选 2"}]
        //根据id获取子节点数据
        //判断该节点是否是父节点
        //全部权限列表
        List<Dict> dictList = dictDao.findListByParentId(id);
        List<Map<String, Object>> znodes= new ArrayList<>();
        for (Dict dict : dictList) {
            Integer count = dictDao.countIsParent(dict.getId());
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",dict.getId());
            map.put("isParent", count > 0);
            map.put("name",dict.getName());
            znodes.add(map);
            
        }
        return znodes;
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        Dict dict = dictDao.getByDictCode(dictCode);
        if(dict==null) return null;
        return dictDao.findListByParentId(dict.getId());
    }
    @Override
    public List<Dict> findListByParentId(Long parentId) {
        return dictDao.findListByParentId(parentId);
    }

    @Override
    public String getNameById(Long houseTypeId) {
        String name = dictDao.getNameById(houseTypeId);
        return name;
    }

    @Override
    protected BaseDao<Dict> getEntityDao() {
        return dictDao;
    }
}
