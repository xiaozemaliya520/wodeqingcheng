package com.qingcheng.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.entity.PageResult;
import com.qingcheng.entity.Result;
import com.qingcheng.pojo.user.Orders;
import com.qingcheng.service.user.OrdersService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Reference
    private OrdersService ordersService;

    @GetMapping("/findAll")
    public List<Orders> findAll(){
        return ordersService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Orders> findPage(int page, int size){
        return ordersService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Orders> findList(@RequestBody Map<String,Object> searchMap){
        return ordersService.findList(searchMap);
    }

    @PostMapping("/findPage")
    public PageResult<Orders> findPage(@RequestBody Map<String,Object> searchMap,int page, int size){
        return  ordersService.findPage(searchMap,page,size);
    }

    @GetMapping("/findById")
    public Orders findById(Integer id){
        return ordersService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Orders orders){
        ordersService.add(orders);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Orders orders){
        ordersService.update(orders);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Integer id){
        ordersService.delete(id);
        return new Result();
    }

}
