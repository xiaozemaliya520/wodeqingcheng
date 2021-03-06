package com.qingcheng.controller;


import com.alibaba.dubbo.config.annotation.Reference;

import com.qingcheng.service.goods.SkuSearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class SearchController {
    @Reference
    private SkuSearchService skuSearchService;

    @GetMapping("/search")
    public String search(Model model, @RequestParam Map<String, String> searchMap) throws Exception {
        searchMap = WebUtil.convertCharsetToUTF8(searchMap);
        Map result = skuSearchService.search(searchMap);

        StringBuffer url = new StringBuffer("/search.do");
        for (String key : searchMap.keySet()) {
                url.append("&"+key+"="+searchMap.get(key));
        }
        model.addAttribute("url",url);
        model.addAttribute("searchMap",searchMap);


        model.addAttribute("result", result);
        return "search";
    }
}
