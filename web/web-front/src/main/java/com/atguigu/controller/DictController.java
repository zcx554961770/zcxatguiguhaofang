package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/dict")
@CrossOrigin
@SuppressWarnings({"unchecked", "rawtypes"})
public class DictController extends BaseController {

    @Reference
    private DictService dictService;

    /**
     * 根据上级id获取子节点数据列表
     * @param parentId
     * @return
     */
    @GetMapping(value = "findListByParentId/{parentId}")
    public Result<List<Dict>> findListByParentId(@PathVariable Long parentId) {
        List<Dict> list = dictService.findListByParentId(parentId);
        return Result.ok(list);
    }

    /**
     * 根据编码获取子节点数据列表
     * @param dictCode
     * @return
     */
    @GetMapping(value = "findListByDictCode/{dictCode}")
    public Result<List<Dict>> findListByDictCode(@PathVariable String dictCode) {
        List<Dict> list = dictService.findListByDictCode(dictCode);
        return Result.ok(list);
    }

}