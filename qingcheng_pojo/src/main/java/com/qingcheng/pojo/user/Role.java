package com.qingcheng.pojo.user;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * role实体类
 * @author Administrator
 *
 */
@Table(name="sys_role")
public class Role implements Serializable{

	@Id
	private Integer id;//id


	

	private String rolename;//rolename

	private String roleDesc;//roleDesc

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}


	
}
