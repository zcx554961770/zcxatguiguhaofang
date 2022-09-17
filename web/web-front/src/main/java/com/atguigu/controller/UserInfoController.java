package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.vo.LoginVo;
import com.atguigu.vo.RegisterVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.atguigu.util.MD5;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//返回json的Conrroller
@RestController
@RequestMapping(value = "/userInfo")
//跨域
@CrossOrigin
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserInfoController extends BaseController {

    @Reference
    public UserInfoService userInfoService;

    /**
     * 会员注册
     * @param registerVo
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpServletRequest httpServletRequest){
        String nickName = registerVo.getNickName();
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //空值验证
        if(StringUtils.isEmpty(nickName) ||
                StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)){
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        // TODO: 2022/9/13  
//        String currentCode = (String)httpServletRequest.getSession().getAttribute("CODE");
//        if(!code.equals(currentCode)){
//            return Result.build(null,ResultCodeEnum.CODE_ERROR);
//        }
        //电话验证
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if(userInfo!=null) return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);

        userInfo = new UserInfo();
        userInfo.setNickName(nickName);
        userInfo.setPhone(phone);
        userInfo.setPassword(MD5.encrypt(password));
        userInfo.setStatus(1);
        userInfoService.insert(userInfo);
        return Result.ok();

    }
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo,HttpServletRequest httpServletRequest){
        String password = loginVo.getPassword();
        String phone = loginVo.getPhone();

        //校验参数
        if(StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if(null==userInfo) return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        //校验密码
        if(!MD5.encrypt(password).equals(userInfo.getPassword())) return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        //校验是否被禁用
        if(userInfo.getStatus()==0){
            return Result.build(null,ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        httpServletRequest.getSession().setAttribute("USER",userInfo);

        Map<String, Object> map = new HashMap<>();
        map.put("phone", userInfo.getPhone());
        map.put("nickName", userInfo.getNickName());
        return Result.ok(map);
    }

    @GetMapping("/sendCode/{moble}")
    public Result sendCode(@PathVariable String moble, HttpServletRequest request) {
        String code = "1111";
        request.getSession().setAttribute("CODE", code);
        return Result.ok(code);
    }
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        request.getSession().removeAttribute("USER");
        return Result.ok();
    }
}
