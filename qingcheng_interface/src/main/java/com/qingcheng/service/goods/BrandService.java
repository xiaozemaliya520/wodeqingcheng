package com.qingcheng.service.goods;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.*;

/**
 * brand业务逻辑层
 */
public interface BrandService {


    public List<Brand> findAll();


    public PageResult<Brand> findPage(int page, int size);


    public List<Brand> findList(Map<String, Object> searchMap);


    public PageResult<Brand> findPage(Map<String, Object> searchMap, int page, int size);


    public Brand findById(Integer id);

    public void add(Brand brand);


    public void update(Brand brand);


    public void delete(Integer id);

    public List<Brand> findByCategory( String categoryName);


}
