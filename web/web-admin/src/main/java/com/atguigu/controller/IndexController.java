package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@SuppressWarnings({"uncheaked","rawtypes"})
public class IndexController {
    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;
    private final static String PAGE_AUTH     = "frame/auth";
    private final static String PAGE_INDEX = "frame/index";
    private final static String PAGE_MAIN = "frame/main";
    private final static String PAGE_LOGIN = "frame/login";

    @GetMapping("/auth")
    public String auth() {
        return PAGE_AUTH;
    }

    /**
     * 框架首页
     *
     * @return
     */

    @GetMapping("/")
    public String index(ModelMap model, HttpServletRequest request) {
        //后续替换为当前登录用户id

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Admin admin = adminService.getByUsername(user.getUsername());
        Long adminId = admin.getId();

        List<Permission> permissionList = permissionService.findMenuPermissionByAdminId(adminId);
        model.addAttribute("admin",admin);
        model.addAttribute("permissionList",permissionList);

        return PAGE_INDEX;
    }

    @GetMapping("/main")
    public String main(){
        return PAGE_MAIN;
    }
    @GetMapping("/login")
    public String login(){

        return PAGE_LOGIN;
    }
    @GetMapping("/getInfo")
    @ResponseBody
    public  Object getInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal();
    }


}
