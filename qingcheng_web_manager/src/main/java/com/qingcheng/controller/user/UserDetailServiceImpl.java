package com.qingcheng.controller.user;


import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.pojo.system.Admin;
import com.qingcheng.service.system.AdminService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDetailServiceImpl implements UserDetailsService {
    @Reference
    private AdminService adminService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Map map=new HashMap();
        map.put("loginName",s);
        map.put("status","1");
        List<Admin> adminList=adminService.findList(map);
        if(adminList.size()==0){
            return null;
        }


        System.out.println("jingguole");
        List<GrantedAuthority> grantedAuthorityList=new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return  new User(s,adminList.get(0).getPassword(),grantedAuthorityList);
    }
}
