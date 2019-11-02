package com.qingcheng.service.user;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.user.Orders;

import java.util.*;

/**
 * orders业务逻辑层
 */
public interface OrdersService {


    public List<Orders> findAll();


    public PageResult<Orders> findPage(int page, int size);


    public List<Orders> findList(Map<String,Object> searchMap);


    public PageResult<Orders> findPage(Map<String,Object> searchMap,int page, int size);


    public Orders findById(Integer id);

    public void add(Orders orders);


    public void update(Orders orders);


    public void delete(Integer id);

}
