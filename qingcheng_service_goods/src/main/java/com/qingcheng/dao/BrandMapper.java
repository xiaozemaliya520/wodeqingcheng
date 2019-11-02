package com.qingcheng.dao;

import com.qingcheng.pojo.goods.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    @Select("SELECT  b.name  ,b.image  FROM tb_brand b WHERE" +
            "  b.id  IN (SELECT brand_id FROM tb_category_brand cb WHERE cb.`category_id` IN(SELECT id  FROM  tb_category t WHERE  t.`name`= #{name}  ))ORDER BY seq")
    public List<Brand> findByCategory(@Param("name") String name);
}
