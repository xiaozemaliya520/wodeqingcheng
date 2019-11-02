package com.qingcheng.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.OrdersMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.user.Orders;
import com.qingcheng.service.user.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    /**
     * 返回全部记录
     * @return
     */
    public List<Orders> findAll() {
        return ordersMapper.selectAll();
    }

    /**
     * 分页查询
     * @param page 页码
     * @param size 每页记录数
     * @return 分页结果
     */
    public PageResult<Orders> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<Orders> orderss = (Page<Orders>) ordersMapper.selectAll();
        return new PageResult<Orders>(orderss.getTotal(),orderss.getResult());
    }

    /**
     * 条件查询
     * @param searchMap 查询条件
     * @return
     */
    public List<Orders> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return ordersMapper.selectByExample(example);
    }

    /**
     * 分页+条件查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    public PageResult<Orders> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page,size);
        Example example = createExample(searchMap);
        Page<Orders> orderss = (Page<Orders>) ordersMapper.selectByExample(example);
        return new PageResult<Orders>(orderss.getTotal(),orderss.getResult());
    }

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    public Orders findById(Integer id) {
        return ordersMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增
     * @param orders
     */
    public void add(Orders orders) {
        ordersMapper.insert(orders);
    }

    /**
     * 修改
     * @param orders
     */
    public void update(Orders orders) {
        ordersMapper.updateByPrimaryKeySelective(orders);
    }

    /**
     *  删除
     * @param id
     */
    public void delete(Integer id) {
        ordersMapper.deleteByPrimaryKey(id);
    }

    /**
     * 构建查询条件
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap){
        Example example=new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
            // ordertime
            if(searchMap.get("ordertime")!=null && !"".equals(searchMap.get("ordertime"))){
                criteria.andLike("ordertime","%"+searchMap.get("ordertime")+"%");
            }

            // id
            if(searchMap.get("id")!=null ){
                criteria.andEqualTo("id",searchMap.get("id"));
            }
            // uid
            if(searchMap.get("uid")!=null ){
                criteria.andEqualTo("uid",searchMap.get("uid"));
            }

        }
        return example;
    }

}
