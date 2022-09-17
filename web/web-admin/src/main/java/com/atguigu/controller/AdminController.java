package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping(value = "/admin")
@SuppressWarnings({"unchecked", "rawtypes"})
public class AdminController extends BaseController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Reference
    private RoleService roleService;
    @Reference
    private AdminService adminService;
    private final static String PAGE_ASSGIN_SHOW = "admin/assginShow";

    private final static String LIST_ACTION = "redirect:/admin";

    private final static String PAGE_INDEX = "admin/index";
    private final static String PAGE_CREATE = "admin/create";
    private final static String PAGE_EDIT = "admin/edit";
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String PAGE_UPLOED_SHOW = "admin/upload";
    /**
     * 进入分配角色页面
     *
     * @param adminId
     * @return
     */
    @GetMapping("/assignShow/{adminId}")
    public String assignShow(ModelMap model, @PathVariable Long adminId) {
        Map<String, Object> roleMap = roleService.findRoleByAdminId(adminId);
        model.addAllAttributes(roleMap);
        model.addAttribute("adminId", adminId);
        return PAGE_ASSGIN_SHOW;
    }

    /**
     * 根据用户分配角色
     *
     * @param adminId
     * @param roleIds
     * @return
     */
    @PostMapping("/assignRole")
    public String assignRole(Long adminId, Long[] roleIds) {
        roleService.saveUserRoleRealtionShip(adminId, roleIds);
        return PAGE_SUCCESS;
    }



    @GetMapping("/create")
    public String create() {
        return PAGE_CREATE;
    }

    //主页面
    @RequestMapping
    public String index(ModelMap model, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        PageInfo<Admin> page = adminService.findPage(filters);
        model.addAttribute("page", page);
        model.addAttribute("filters", filters);
        return PAGE_INDEX;
    }

    @PostMapping("/save")
    public String save(Admin admin, Model model) {


        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        //设置默认头像
        admin.setHeadUrl("http://47.93.148.192:8080/group1/M00/03/F0/rBHu8mHqbpSAU0jVAAAgiJmKg0o148.jpg");
        adminService.insert(admin);
        return this.successPage(model, "添加用户成功");
    }

    @GetMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable Long id) {
        Admin admin = adminService.getById(id);
        model.addAttribute("admin", admin);
        return PAGE_EDIT;
    }

    @PostMapping("/update")
    public String update(Admin admin) {
        Integer update = adminService.update(admin);
        return PAGE_SUCCESS;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        adminService.delete(id);
        return PAGE_SUCCESS;
    }

    @GetMapping("/uploadShow/{id}")
    public String uploadShow(ModelMap model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return PAGE_UPLOED_SHOW;
    }

    /*
     * 头像上传
     * */
    @PostMapping("/upload/{id}")
    public String upload(Model model, @PathVariable Long id,
                         @RequestParam(value = "file") MultipartFile file,
                         HttpServletRequest request) throws IOException {
        String newFileName = UUID.randomUUID().toString();
        QiniuUtils.upload2Qiniu(file.getBytes(), newFileName);
        String url = "http://ri4vyud8x.hn-bkt.clouddn.com/" + newFileName;
        Admin admin = new Admin();
        admin.setId(id);
        admin.setHeadUrl(url);
        adminService.update(admin);
        return this.successPage(model, "头像上传成功!");

    }
}