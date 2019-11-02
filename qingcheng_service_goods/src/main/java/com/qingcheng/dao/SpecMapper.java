package com.qingcheng.dao;

import com.qingcheng.pojo.goods.Spec;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SpecMapper extends Mapper<Spec> {

    @Select("SELECT  s.name ,s.options " +
            "FROM tb_spec   s" +
            "  WHERE    s.template_id  IN (SELECT c.`template_id`  FROM  tb_category c WHERE c.`name`=#{name})")

    public List<Map> specList(@Param("name") String categoryName);

}
