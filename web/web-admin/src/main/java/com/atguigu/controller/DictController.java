package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/dict")
@SuppressWarnings({"unchecked", "rawtypes"})
public class DictController extends BaseController {
    @Reference
    public DictService dictService;

    private final static String PAGE_INDEX = "dict/index";


    @GetMapping(value = "findZnodes")
    @ResponseBody
    public Result findByParentId(@RequestParam (value = "id",defaultValue = "0") Long id){
        List<Map<String, Object>> znodes = dictService.findZnodes(id);

        return Result.ok( znodes);
    }
    @GetMapping
    public String index(ModelMap model){
        return PAGE_INDEX;
    }
    @GetMapping(value = "findListByParentId/{parentId}")
    @ResponseBody
    public Result<List<Dict>> findListByParentId(@PathVariable Long parentId) {
        List<Dict> list = dictService.findListByParentId(parentId);
        return Result.ok(list);
    }

    @GetMapping(value = "findListByDictCode/{dictCode}")
    @ResponseBody
    public Result<List<Dict>> findListByDictCode(@PathVariable String dictCode ){
        List<Dict> listByDictCode = dictService.findListByDictCode(dictCode);
        return Result.ok(listByDictCode);
    }
}
