package com.qingcheng.controller.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qingcheng.entity.PageResult;
import com.qingcheng.entity.Result;
import com.qingcheng.pojo.system.Admin;
import com.qingcheng.pojo.system.Admin_RoleIdList;
import com.qingcheng.service.system.AdminService;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Reference
    private AdminService adminService;

    @GetMapping("/findAll")
    public List<Admin> findAll(){
        return adminService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Admin> findPage(int page, int size){
        return adminService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Admin> findList(@RequestBody Map<String,Object> searchMap){
        return adminService.findList(searchMap);
    }

    @PostMapping("/findPage")
    public PageResult<Admin> findPage(@RequestBody Map<String,Object> searchMap,int page, int size){
        return  adminService.findPage(searchMap,page,size);
    }

    @GetMapping("/findById")
    public Admin findById(Integer id){
        return adminService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Admin admin){
        adminService.add(admin);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Map<String,Object> searchMap){
        JSONObject jsonObject = (JSONObject) searchMap.get("admin");
        Admin admin = jsonObject.toJavaObject(jsonObject, Admin.class);
        JSONObject jsonObject2 = (JSONObject) searchMap.get("roleId");
        List<Integer> array = JSONObject.parseArray(jsonObject2.toJSONString(), Integer.class);
        Admin_RoleIdList admin_roleIdList = new Admin_RoleIdList();
        admin_roleIdList.setAdmin(admin);
        admin_roleIdList.setRoleIds(array);
        adminService.update(admin_roleIdList);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Integer id){
        adminService.delete(id);
        return new Result();
    }
    @PostMapping("/addAdmin_Role")
    public Result addAdmin_Role(@RequestBody Map<String,Object> searchMap){
        JSONObject jsonObject = (JSONObject) searchMap.get("admin");
        Admin admin = JSONObject.toJavaObject(jsonObject, Admin.class);
        JSONArray ids=(JSONArray)searchMap.get("roleId");
        List<Integer> roleIds=JSONObject.parseArray(ids.toJSONString(),Integer.class);
        Admin_RoleIdList list = new Admin_RoleIdList();
        list.setAdmin(admin);
        list.setRoleIds(roleIds);
        System.out.println(list);
        adminService.addAdminRoleList(list);
        return new Result();
    }

}
