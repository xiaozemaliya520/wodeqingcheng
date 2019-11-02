package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qingcheng.dao.CategoryReportMapper;
import com.qingcheng.pojo.order.CategoryReport;
import com.qingcheng.service.order.CategoryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CategoryReportService.class)
public class CategoryReportServiceImpl implements CategoryReportService {
    @Autowired
    private CategoryReportMapper categoryReportMapper;
    @Override
    public List<CategoryReport> categoryReport(LocalDate date) {

      return  categoryReportMapper.categoryReport(date);

    }

    @Override
    @Transactional
    public void createDate() {
        LocalDate date = LocalDate.now().minusDays(1);
        List<CategoryReport> categoryReportList= categoryReportMapper.categoryReport(date);
        for (CategoryReport categoryReport : categoryReportList) {

            categoryReportMapper.insert(categoryReport);
        }

    }

    @Override
    public List<Map> categoryCount(String date1, String date2) {
        return categoryReportMapper.categoryCount(date1,date2);
    }
}
