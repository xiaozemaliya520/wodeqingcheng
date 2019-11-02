package com.qingcheng.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.AdminMapper;
import com.qingcheng.dao.Admin_RoleMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.system.Admin;
import com.qingcheng.pojo.system.Admin_Role;
import com.qingcheng.pojo.system.Admin_RoleIdList;
import com.qingcheng.pojo.system.Role;
import com.qingcheng.service.system.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = AdminService.class)
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 返回全部记录
     * @return
     */
    public List<Admin> findAll() {
        return adminMapper.selectAll();
    }

    /**
     * 分页查询
     * @param page 页码
     * @param size 每页记录数
     * @return 分页结果
     */
    public PageResult<Admin> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<Admin> admins = (Page<Admin>) adminMapper.selectAll();
        return new PageResult<Admin>(admins.getTotal(),admins.getResult());
    }

    /**
     * 条件查询
     * @param searchMap 查询条件
     * @return
     */
    public List<Admin> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return adminMapper.selectByExample(example);
    }

    /**
     * 分页+条件查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    public PageResult<Admin> findPage(Map<String, Object> searchMap, int page, int size) {
        System.out.println(searchMap);
        System.out.println(page);
        PageHelper.startPage(page,size);
        Example example = createExample(searchMap);
        Page<Admin> admins = (Page<Admin>) adminMapper.selectByExample(example);
        return new PageResult<Admin>(admins.getTotal(),admins.getResult());
    }

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    public Admin findById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增
     * @param admin
     */
    public void add(Admin admin) {
        adminMapper.insert(admin);
    }

    /**
     * 修改
     * @param admin_roleIdList
     */
    @Transactional
    public void update(Admin_RoleIdList admin_roleIdList) {
        Admin admin = admin_roleIdList.getAdmin();

        adminMapper.updateByPrimaryKey(admin);
        Integer adminId = admin.getId();
        if(adminId!=null){
         Example example=new Example(Admin_Role.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("adminId",adminId);
            List<Admin_Role> admin_roles = admin_roleMapper.selectByExample(example);
            for (Admin_Role admin_role : admin_roles) {
                admin_roleMapper.delete(admin_role);
            }
            List<Integer> roleIds = admin_roleIdList.getRoleIds();
            for (Integer roleId : roleIds) {
                Admin_Role admin_role = new Admin_Role();
                admin_role.setRoleId(roleId);
                admin_role.setAdminId(adminId);
                admin_roleMapper.insert(admin_role);
            }

        }

    }

    /**
     *  删除
     * @param id
     */
    public void delete(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }
    @Autowired
    private Admin_RoleMapper admin_roleMapper;


    @Override
    @Transactional
    public void addAdminRoleList(Admin_RoleIdList admin_roleIdList) {
        Admin admin = admin_roleIdList.getAdmin();
        adminMapper.insert(admin);
        List<Integer> roleIds = admin_roleIdList.getRoleIds();
        for (Integer roleId : roleIds) {
            Admin_Role admin_role = new Admin_Role();
            admin_role.setAdminId(admin.getId());
            admin_role.setRoleId(roleId);
            admin_roleMapper.insert(admin_role);
        }


    }

    /**
     * 构建查询条件
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap){
        Example example=new Example(Admin.class);
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
            // 用户名
            if(searchMap.get("loginName")!=null && !"".equals(searchMap.get("loginName"))){
//                criteria.andLike("loginName","%"+searchMap.get("loginName")+"%");
                criteria.andEqualTo("loginName",searchMap.get("loginName"));
            }
            // 密码
            if(searchMap.get("password")!=null && !"".equals(searchMap.get("password"))){
//                criteria.andLike("password","%"+searchMap.get("password")+"%");
                criteria.andEqualTo("password",searchMap.get("password"));
            }
            // 状态
            if(searchMap.get("status")!=null && !"".equals(searchMap.get("status"))){
                criteria.andLike("status","%"+searchMap.get("status")+"%");
            }

            // id
            if(searchMap.get("id")!=null ){
                criteria.andEqualTo("id",searchMap.get("id"));
            }

        }
        return example;
    }

}
