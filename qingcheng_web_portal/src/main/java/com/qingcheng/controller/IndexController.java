package com.qingcheng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.pojo.business.Ad;
import com.qingcheng.service.business.AdService;
import com.qingcheng.service.goods.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller

public class IndexController {
    @Reference
    private AdService adService;
    @Reference
    private CategoryService categoryService;
   @GetMapping("index")
    public String index(Model model){
       List<Ad> adList = adService.findAdByposition("web_index_lb");
       System.out.println(adList);

       model.addAttribute("lbt",adList);
       model.addAttribute("categoryList",categoryService.findCategoryTree());
       return "index";
   }
}
