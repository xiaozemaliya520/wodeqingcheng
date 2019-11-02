package com.qingcheng.pojo.user;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * orders实体类
 * @author Administrator
 *
 */
@Table(name="orders")
public class Orders implements Serializable{

	@Id
	private Integer id;//id


	

	private String ordertime;//ordertime

	private Double total;//total

	private Integer uid;//uid

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}


	
}
