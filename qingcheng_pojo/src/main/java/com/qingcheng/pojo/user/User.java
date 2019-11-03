package com.qingcheng.pojo.user;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * user实体类
 * @author Administrator
 *
 */
@Table(name="user")
public class User implements Serializable{

	@Id
	private Integer id;//id
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	private String username;//username

	private String password;//password

	private String birthday;//birthday

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	
}
