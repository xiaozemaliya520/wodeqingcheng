package com.qingcheng.dao;

import com.qingcheng.pojo.order.Order;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface OrderMapper extends Mapper<Order> {
    @Select("select count(username) from tb_order order by username")
    public int  xiadanrenshu();
    @Select("select count(id) from tb_order order by id")
    public int dingdanshu();
    @Select("select sum(pay_money) from tb_order  where pay_status = '2'")
    public int tuikuanjiane();
    @Select("select count(username) from tb_order where pay_status = '1'")
    public  int fukuanrenshu();
    @Select("select sum(pay_money) from tb_order where pay_status='1' ")
    public int fukianjine();

}
