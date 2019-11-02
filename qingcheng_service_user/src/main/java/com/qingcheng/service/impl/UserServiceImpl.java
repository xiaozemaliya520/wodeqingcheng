package com.qingcheng.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.UserMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.user.User;
import com.qingcheng.service.user.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 返回全部记录
     * @return
     */
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    /**
     * 分页查询
     * @param page 页码
     * @param size 每页记录数
     * @return 分页结果
     */
    public PageResult<User> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<User> users = (Page<User>) userMapper.selectAll();
        return new PageResult<User>(users.getTotal(),users.getResult());
    }

    /**
     * 条件查询
     * @param searchMap 查询条件
     * @return
     */
    public List<User> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return userMapper.selectByExample(example);
    }

    /**
     * 分页+条件查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    public PageResult<User> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page,size);
        Example example = createExample(searchMap);
        Page<User> users = (Page<User>) userMapper.selectByExample(example);
        return new PageResult<User>(users.getTotal(),users.getResult());
    }

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增
     * @param user
     */
    public void add(User user) {
        userMapper.insert(user);
    }

    /**
     * 修改
     * @param user
     */
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     *  删除
     * @param id
     */
    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void sendSms(String phone) {
            int max=999999;
            int min=10000;
        Random random=new Random();
            int code=random.nextInt(max);
            if(code<min){
                code+=min;
            }
        System.out.println("验证码是："+code);
            redisTemplate.boundValueOps("code_"+phone).set(code+"");
            redisTemplate.boundValueOps("code_"+phone).expire(5, TimeUnit.MINUTES);//存活五分钟
        Map<String,String> map=new HashMap<>();
        map.put("phone",phone);
        map.put("code",code+"");
        rabbitTemplate.convertAndSend("","queue.sms", JSON.toJSONString(map));




    }

    @Override
    public void add(User user, String smsCode) {

    }

    /**
     * 构建查询条件
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap){
        Example example=new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
            // username
            if(searchMap.get("username")!=null && !"".equals(searchMap.get("username"))){
                criteria.andLike("username","%"+searchMap.get("username")+"%");
            }
            // password
            if(searchMap.get("password")!=null && !"".equals(searchMap.get("password"))){
                criteria.andLike("password","%"+searchMap.get("password")+"%");
            }
            // birthday
            if(searchMap.get("birthday")!=null && !"".equals(searchMap.get("birthday"))){
                criteria.andLike("birthday","%"+searchMap.get("birthday")+"%");
            }

            // id
            if(searchMap.get("id")!=null ){
                criteria.andEqualTo("id",searchMap.get("id"));
            }

        }
        return example;
    }

}
