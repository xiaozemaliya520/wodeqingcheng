package com.qingcheng.service.jiaoyi;

import com.qingcheng.pojo.goods.Jiaoyitongji;

import java.time.LocalDate;
import java.util.List;

public interface JiaoyitongjiService {

    public List<Jiaoyitongji> findAll();

    List<Jiaoyitongji> findByDate(String date1, String date2);

    void add();
}
